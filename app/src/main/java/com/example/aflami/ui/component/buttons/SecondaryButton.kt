package com.example.aflami.ui.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aflami.designsystem.theme.Theme

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
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
            backgroundColor = Color.Transparent,
            contentColor = Theme.color.primary,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Theme.color.stroke,
            errorBackgroundColor = Color.Transparent,
            errorContentColor = Theme.color.statusColors.redAccent,
        ),
        border = BorderStroke(
            1.dp,
            Theme.color.disable
        ),
        shape = shape,
        content = content
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun SecondaryButtonPreview() {
    SecondaryButton(
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
private fun SecondaryButtonLoadingPreview() {
    SecondaryButton(
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
private fun SecondaryButtonDisabledPreview() {
    SecondaryButton(
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
private fun SecondaryButtonErrorPreview() {
    SecondaryButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}
