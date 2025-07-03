package com.berlin.safeimageviewer

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import com.berlin.safeimageviewer.ml.SavedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Composable
fun SafeImage(
    imageUri: String,

    ) {
    val context = LocalContext.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isSafe by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(imageUri) {
        val request = ImageRequest.Builder(context)
            .data(imageUri)
            .allowHardware(false)
            .build()

        val result = context.imageLoader.execute(request)
        val drawable = (result as? SuccessResult)?.image
        val bmp = drawable?.toBitmap()

        if (bmp != null) {
            bitmap = bmp

            val byteBuffer = bitmapToByteBuffer(bmp)
            val input = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            input.loadBuffer(byteBuffer)

            val model = SavedModel.newInstance(context)
            val output = model.process(input).outputFeature0AsTensorBuffer.floatArray
            model.close()

            val highest = output.indices.maxByOrNull { output[it] } ?: -1
            isSafe = highest == 2

        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        isSafe?.let { safe ->
            bitmap?.let { image ->
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        
                        .blur(

                            radius = if (!safe) 40.dp else 0.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                        .shadow(elevation = if (!safe) 1.dp else 0.dp)
                    ,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


fun bitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
    val inputSize = 224
    val byteBuffer = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)
    byteBuffer.order(ByteOrder.nativeOrder())

    val scaled = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
    for (y in 0 until inputSize) {
        for (x in 0 until inputSize) {
            val pixel = scaled.getPixel(x, y)

            val r = (pixel shr 16 and 0xFF) / 255f
            val g = (pixel shr 8 and 0xFF) / 255f
            val b = (pixel and 0xFF) / 255f

            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }
    }

    return byteBuffer
}

@Composable
@Preview(showBackground = true)
fun SafeImagePrev(){
    SafeImage(
        imageUri = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?w=500",
    )
}