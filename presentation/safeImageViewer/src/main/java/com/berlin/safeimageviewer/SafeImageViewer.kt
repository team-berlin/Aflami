package com.berlin.safeimageviewer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.request.bitmapConfig
import coil3.toBitmap
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun SafeImageViewer(
    model: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale? = ContentScale.Crop,
    error: Painter? = null,
    fallback: Painter? = null,
    placeholder: Painter? = null,
    checkIsSafeImage: Boolean = true,
    alignment: Alignment = Alignment.TopCenter,
) {
    val context = LocalContext.current
    val modelManager = remember {
        EntryPointAccessors.fromApplication(
            context, FireBaseModelManagerEntryPoint::class.java
        ).modelManager()
    }
    val isModelDownloaded = remember { modelManager.isModelDownloaded.value }

    var result by remember { mutableStateOf<ImageClassificationResult?>(null) }
    var displayBitmap by remember { mutableStateOf<Bitmap?>(null) }
    if (!isModelDownloaded) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            error = error,
            fallback = fallback,
            placeholder = placeholder,
            alignment = alignment,
            modifier = modifier,
            contentScale = contentScale ?: ContentScale.Crop
        )
    } else {
        LaunchedEffect(model) {
            val restrictions = modelManager.getCurrentRestriction()
            if (checkIsSafeImage) {
                result = classifyImage(context, model, modelManager, restrictions)
                displayBitmap = result?.bitmap
            } else {
                withContext(Dispatchers.IO) {
                    val res = context.imageLoader.execute(
                        ImageRequest.Builder(context).data(model).allowHardware(false)
                            .bitmapConfig(Bitmap.Config.ARGB_8888).build()
                    )
                    val bmp = (res as? SuccessResult)?.image?.toBitmap()
                    displayBitmap = bmp
                }
            }
        }
    }
    displayBitmap?.let { bitmap ->
        val shouldBlur = when {
            !checkIsSafeImage -> true

            else -> result?.let {
                !it.isSafe || it.isFemale

            } == true
        }

        val blurredBitmap = remember(bitmap, shouldBlur) {
            if (shouldBlur) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    bitmap
                } else {
                    blurBitmapRenderScript(context, bitmap, 25f)
                }
            } else {
                bitmap
            }
        }
        AsyncImage(
            model = blurredBitmap,
            contentDescription = contentDescription,
            error = error,
            fallback = fallback,
            placeholder = placeholder,
            alignment = alignment,
            modifier = modifier
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && shouldBlur) {
                        Modifier.blur(
                            radius = 16.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                    } else Modifier
                )
                .shadow(elevation = if (shouldBlur) 1.dp else 0.dp),
            contentScale = contentScale ?: ContentScale.Crop
        )
    }
}


@Suppress("DEPRECATION")
fun blurBitmapRenderScript(context: Context, bitmap: Bitmap, radius: Float): Bitmap {
    val inputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    val rs = RenderScript.create(context)
    val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

    val input = Allocation.createFromBitmap(rs, inputBitmap)
    val output = Allocation.createFromBitmap(rs, outputBitmap)

    blurScript.setRadius(radius.coerceIn(0f, 25f))
    blurScript.setInput(input)
    blurScript.forEach(output)

    output.copyTo(outputBitmap)
    rs.destroy()

    return outputBitmap
}

suspend fun classifyImage(
    context: Context,
    imageUri: String,
    modelManager: FireBaseModelManager,
    contentRestrictions: String?
): ImageClassificationResult? = withContext(Dispatchers.IO) {
    val result = context.imageLoader.execute(
        ImageRequest.Builder(context).data(imageUri).allowHardware(false)
            .bitmapConfig(Bitmap.Config.ARGB_8888).build()
    )

    val drawable = (result as? SuccessResult)?.image ?: return@withContext null
    val bitmap = drawable.toBitmap()
    return@withContext try {
        when {
            classifyGender(bitmap, modelManager, contentRestrictions) -> ImageClassificationResult(
                bitmap, isSafe = true, isFemale = true
            )

            classifyNSFW(bitmap, modelManager, contentRestrictions) -> ImageClassificationResult(
                bitmap, isSafe = false, isFemale = false
            )

            else -> ImageClassificationResult(bitmap, isSafe = true, isFemale = false)
        }
    } catch (e: Exception) {
        ImageClassificationResult(
            bitmap, isSafe = false, isFemale = true
        )
    }

}

fun classifyNSFW(
    bitmap: Bitmap, modelManager: FireBaseModelManager, contentRestrictions: String?
): Boolean {
    val nsfwBuffer = bitmap.toModelByteBuffer(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
    val nsfwInput = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
    nsfwInput.loadBuffer(nsfwBuffer)
    val nsfwOutput = TensorBuffer.createFixedSize(intArrayOf(1, 5), DataType.FLOAT32)

    modelManager.nsfwInterpreter?.run(nsfwInput.buffer, nsfwOutput.buffer.rewind())
    val nsfwModelScore = nsfwOutput.floatArray
    val pornScore = nsfwModelScore.getOrNull(3) ?: 0f
    val sexyScore = nsfwModelScore.getOrNull(4) ?: 0f
    val hentaiScore = nsfwModelScore.getOrNull(1) ?: 0f
    val inappropriateScore = pornScore + sexyScore + hentaiScore
    val isSafe = when (contentRestrictions) {
        STRICT_MODERATION -> inappropriateScore <= STRICT_MODERATION_THRESHOLD
        MODERATE_MODERATION -> inappropriateScore <= DEFAULT_IMAGE_MODERATION_THRESHOLD
        else -> true
    }
    return isSafe
}

fun classifyGender(
    bitmap: Bitmap, modelManager: FireBaseModelManager, contentRestrictions: String?
): Boolean {
    val genderBuffer = bitmap.toModelByteBuffer(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)
    val genderInput = TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)
    genderInput.loadBuffer(genderBuffer)

    val genderOutput = TensorBuffer.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)

    modelManager.genderInterpreter?.run(genderInput.buffer, genderOutput.buffer.rewind())
    val genderModelScore = genderOutput.floatArray[1]

    val isFemale = when (contentRestrictions) {
        STRICT_MODERATION -> genderModelScore > DEFAULT_IMAGE_MODERATION_THRESHOLD
        MODERATE_MODERATION -> genderModelScore > 0.5f
        else -> false
    }

    return isFemale
}

fun Bitmap.toModelByteBuffer(
    inputShape: IntArray, dataType: DataType
): ByteBuffer {
    val width = inputShape[1]
    val height = inputShape[2]
    val channels = inputShape[3]

    val resized = Bitmap.createScaledBitmap(this, width, height, true)
    val byteBuffer = ByteBuffer.allocateDirect(
        width * height * channels * if (dataType == DataType.FLOAT32) 4 else 1
    )
    byteBuffer.order(ByteOrder.nativeOrder())

    val intValues = IntArray(width * height)
    resized.getPixels(intValues, 0, width, 0, 0, width, height)
    for (pixel in intValues) {
        val r = (pixel shr 16) and 0xFF
        val g = (pixel shr 8) and 0xFF
        val b = pixel and 0xFF

        if (dataType == DataType.FLOAT32) {
            byteBuffer.putFloat(r / 255f)
            byteBuffer.putFloat(g / 255f)
            byteBuffer.putFloat(b / 255f)
        } else {
            byteBuffer.put(r.toByte())
            byteBuffer.put(g.toByte())
            byteBuffer.put(b.toByte())
        }
    }

    return byteBuffer
}

data class ImageClassificationResult(
    val bitmap: Bitmap,
    val isSafe: Boolean,
    val isFemale: Boolean,
)

const val DEFAULT_IMAGE_MODERATION_THRESHOLD = 0.5f
const val STRICT_MODERATION_THRESHOLD = 0.25f


@Composable
@Preview(showBackground = true)
fun SafeImagePrev() {
    SafeImageViewer(
        model = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?w=500",
    )
}