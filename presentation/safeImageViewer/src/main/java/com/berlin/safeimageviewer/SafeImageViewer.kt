package com.berlin.safeimageviewer

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
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
import androidx.core.graphics.scale
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
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

@Composable
fun SafeImageViewer(
    imageUri: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale? = ContentScale.Crop,
    error: Painter? = null,
    fallback: Painter? = null,
    placeholder: Painter? = null,
    blurCheck: Boolean = true,
    alignment: Alignment = Alignment.TopCenter,
) {
    val context = LocalContext.current
    val modelManager = remember {
        EntryPointAccessors.fromApplication(
            context,
            FireBaseModelManagerEntryPoint::class.java
        ).modelManager()
    }
    val isModelDownloaded by remember { modelManager.isModelDownloaded }

    if (!isModelDownloaded) return

    var result by remember { mutableStateOf<ImageClassificationResult?>(null) }
    var displayBitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(imageUri, blurCheck) {
        if (blurCheck) {
            result = classifyImage(context, imageUri, modelManager)
            displayBitmap = result?.bitmap
        } else {
            withContext(Dispatchers.IO) {
                val res = context.imageLoader.execute(
                    ImageRequest.Builder(context)
                        .data(imageUri)
                        .allowHardware(false)
                        .bitmapConfig(Bitmap.Config.ARGB_8888)
                        .build()
                )

                val bmp = (res as? SuccessResult)?.image?.toBitmap()
                displayBitmap = bmp
            }
        }
    }

    displayBitmap?.let { bitmap ->
        val shouldBlur = if (blurCheck) {
            result?.let { !it.isSafe || it.isFemale } == true
        } else true

        val blurredBitmap = remember(bitmap, shouldBlur) {
            if (shouldBlur && Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                blurBitmapRenderScript(context, bitmap, 25f)
            } else bitmap
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
                            radius = 16.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
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
    modelManager: FireBaseModelManager
): ImageClassificationResult? = withContext(Dispatchers.IO) {
    val result = context.imageLoader.execute(
        ImageRequest.Builder(context)
            .data(imageUri)
            .allowHardware(false)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
    )
    // gender model Detection
    val drawable = (result as? SuccessResult)?.image?: return@withContext null
    val bitmap = drawable.toBitmap()

    val genderBuffer = bitmapToByteBuffer(bitmap, 128)
    val genderInput = TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)
    genderInput.loadBuffer(genderBuffer)

    val genderOutput = TensorBuffer.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)
    modelManager.genderInterpreter?.run(genderInput.buffer, genderOutput.buffer.rewind())
    val isFemale = genderOutput.floatArray.indices.maxByOrNull { genderOutput.floatArray[it] } == 1
    if(isFemale) return@withContext ImageClassificationResult(bitmap,false, true)


    // --- NSFW Detection
    val nsfwBuffer = bitmapToByteBuffer(bitmap, 224)
    val nsfwInput = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
    nsfwInput.loadBuffer(nsfwBuffer)

    val nsfwOutput = TensorBuffer.createFixedSize(intArrayOf(1, 5), DataType.FLOAT32)
    modelManager.nsfwInterpreter?.run(nsfwInput.buffer, nsfwOutput.buffer.rewind())
    val isSafe = nsfwOutput.floatArray.indices.maxByOrNull { nsfwOutput.floatArray[it] } == 2


    return@withContext ImageClassificationResult(bitmap, isSafe, false)
}
fun bitmapToByteBuffer(bitmap: Bitmap, size: Int): ByteBuffer {
    val inputImage = bitmap.scale(size, size)
    val byteBuffer = ByteBuffer.allocateDirect(4 * size * size * 3)
    byteBuffer.order(ByteOrder.nativeOrder())

    val intValues = IntArray(size * size)
    inputImage.getPixels(intValues, 0, size, 0, 0, size, size)

    for (pixel in intValues) {
        val r = (pixel shr 16 and 0xFF) / 255.0f
        val g = (pixel shr 8 and 0xFF) / 255.0f
        val b = (pixel and 0xFF) / 255.0f
        byteBuffer.putFloat(r)
        byteBuffer.putFloat(g)
        byteBuffer.putFloat(b)
    }
    return byteBuffer
}


data class ImageClassificationResult(
    val bitmap: Bitmap,
    val isSafe: Boolean,
    val isFemale: Boolean
)
@Composable
@Preview(showBackground = true)
fun SafeImagePrev() {
    SafeImageViewer(
        imageUri = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?w=500",
    )
}