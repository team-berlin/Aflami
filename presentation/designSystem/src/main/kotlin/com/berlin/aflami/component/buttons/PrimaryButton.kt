package com.berlin.aflami.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.berlin.aflami.ui.theme.Theme

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED && state != ButtonState.LOADING,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultPadding,
    buttonColors: ButtonColors = ButtonDefaults.colors(),
    content: @Composable RowScope.() -> Unit
) {
    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = buttonColors.copy(
            backgroundGradient = Brush.verticalGradient(
                colors = listOf(
                    Theme.color.primary,
                    Theme.color.primaryButton
                )
            )
        ),
        shape = shape,
        content = content
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(
        onClick = {},
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonLoadingPreview() {
    PrimaryButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonDisabledPreview() {
    PrimaryButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PrimaryButtonErrorPreview() {
    PrimaryButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}
