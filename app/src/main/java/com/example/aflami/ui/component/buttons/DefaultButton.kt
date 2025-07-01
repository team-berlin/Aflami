package com.example.aflami.ui.component.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aflami.ui.component.SpinningProgressIndicatorLines

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.IDLE,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    shape: Shape = ButtonDefaults.defaultShape,
    colors: ButtonColors = ButtonDefaults.colors(),
    border: BorderStroke? = null,
    isFabType: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor = when (state) {
        ButtonState.IDLE, ButtonState.LOADING -> colors.contentColor
        ButtonState.DISABLED -> colors.disabledContentColor
        ButtonState.ERROR -> colors.errorContentColor
    }
    val backgroundColor = when (state) {
        ButtonState.IDLE, ButtonState.LOADING -> colors.backgroundColor
        ButtonState.DISABLED -> colors.disabledBackgroundColor
        ButtonState.ERROR -> colors.errorBackgroundColor
    }
    Box(
        modifier = modifier
            .clip(shape)
            .then(
                if (colors.backgroundGradient != null && state in listOf(
                        ButtonState.IDLE,
                        ButtonState.LOADING
                    )
                ) Modifier.background(
                    colors.backgroundGradient, shape
                ) else Modifier.background(backgroundColor, shape)
            )
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .clickable(enabled = enabled) { onClick() }
        , contentAlignment = Alignment.Center
    ) {
        val mergedStyle = LocalTextStyle.current.merge(contentColor)
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides mergedStyle,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(contentPadding)
            ) {
                content()
                AnimatedVisibility(state == ButtonState.LOADING) {
                    SpinningProgressIndicatorLines(
                        modifier = Modifier.padding(start = if (state == ButtonState.LOADING && !isFabType) 8.dp else 0.dp),
                        size = 16.dp,
                        lineLength = 4.dp,
                        lineWidth = 3.dp,
                        color = contentColor
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=4000px,height=1500px,dpi=440"
)
@Composable
private fun AllButtonPreview() {
    Row(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            PrimaryButton({}) {
                Text(text = "Submit")
            }
            PrimaryButton(onClick = {}, state = ButtonState.LOADING) {
                Text(text = "Submit")
            }
            PrimaryButton(onClick = {}, state = ButtonState.DISABLED) {
                Text(text = "Submit")
            }
            PrimaryButton(onClick = {}, state = ButtonState.ERROR) {
                Text(text = "Submit")
            }
        }

        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            SecondaryButton({}) {
                Text(text = "Submit")
            }
            SecondaryButton(onClick = {}, state = ButtonState.LOADING) {
                Text(text = "Submit")
            }
            SecondaryButton(onClick = {}, state = ButtonState.DISABLED) {
                Text(text = "Submit")
            }
            SecondaryButton(onClick = {}, state = ButtonState.ERROR) {
                Text(text = "Submit")
            }
        }
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton({}) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {}, state = ButtonState.LOADING) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {}, state = ButtonState.DISABLED) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {}, state = ButtonState.ERROR) {
                Text(text = "Cancel")
            }
        }
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            FabButton({}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
            FabButton(onClick = {}, state = ButtonState.LOADING) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
            FabButton(onClick = {}, state = ButtonState.DISABLED) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
            FabButton(onClick = {}, state = ButtonState.ERROR) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NegativeButton({}) {
                Text(text = "Submit")
            }
            NegativeButton(onClick = {}, state = ButtonState.LOADING) {
                Text(text = "Submit")
            }
            NegativeButton(onClick = {}, state = ButtonState.DISABLED) {
                Text(text = "Submit")
            }
            NegativeButton(onClick = {}, state = ButtonState.ERROR) {
                Text(text = "Submit")
            }
        }
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NegativeTextButton({}) {
                Text(text = "Submit")
            }
            NegativeTextButton(onClick = {}, state = ButtonState.LOADING) {
                Text(text = "Submit")
            }
            NegativeTextButton(onClick = {}, state = ButtonState.DISABLED) {
                Text(text = "Submit")
            }
            NegativeTextButton(onClick = {}, state = ButtonState.ERROR) {
                Text(text = "Submit")
            }
        }
    }
}




