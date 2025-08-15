package com.berlin.aflami.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedPlay() {
    val paddingValues = PaddingValues(10.dp)
    val withBorder = false
    val containerColor: Color = Theme.color.surfaceHigh
    val shape: Shape = RoundedCornerShape(12.dp)


    val alpha2 = remember { Animatable(1f) }
    val rotation2 = remember { Animatable(0f) }

    val alpha3 = remember { Animatable(0.5f) }
    val rotation3 = remember { Animatable(-30f) }

    LaunchedEffect(Unit) {
        while (true) {

           delay(400)

            launch {
                alpha2.animateTo(.5f, tween(400))
                rotation2.animateTo(-30f, tween(400))

                alpha2.animateTo(1f, tween(400))
                rotation2.animateTo(0f, tween(400))
            }
            delay(400)

            launch {
                alpha3.animateTo(.2f, tween(400))
                rotation3.animateTo(-60f, tween(400))

                alpha3.animateTo(0.5f, tween(400))
                rotation3.animateTo(-30f, tween(400))
            }
            delay(400)
        }
    }

    val borderModifier = if (withBorder) {
        Modifier.border(width = 1.dp, color = Color.Gray, shape = CircleShape)
    } else Modifier

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(shape)
            .background(containerColor)
            .then(borderModifier)
            .padding(paddingValues)
    ) {
        Icon(
            painter = painterResource(R.drawable.play_animated),
            contentDescription = "1",
            tint = Theme.color.primary,
            modifier = Modifier

        )

        Icon(
            painter = painterResource(R.drawable.play_animated),
            contentDescription = "2",
            tint = Theme.color.primary,
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotation2.value
                    transformOrigin = TransformOrigin(0.5f, 1f)
                }
                .alpha(alpha2.value)
        )

        Icon(
            painter = painterResource(R.drawable.play_animated),
            contentDescription = "3",
            tint = Theme.color.primary,
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotation3.value
                    transformOrigin = TransformOrigin(0.5f, 1f)
                }
                .alpha(alpha3.value)
        )
    }
}

@Preview
@Composable
fun AnimatedPlayPrev(){
    AnimatedPlay()
}


