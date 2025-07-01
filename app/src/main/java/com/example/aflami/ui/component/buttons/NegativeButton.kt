package com.example.aflami.ui.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.example.aflami.designsystem.theme.Theme

@Composable
fun NegativeButton(
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
            backgroundColor = Theme.color.statusColors.redVariant,
            contentColor =Theme.color.statusColors.redAccent,
        ),
        shape = shape,
        content = content
    )
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun NegativeButtonPreview() {
    NegativeButton(
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
private fun NegativeButtonLoadingPreview() {
    NegativeButton(
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
private fun NegativeButtonDisabledPreview() {
    NegativeButton(
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
private fun NegativeButtonErrorPreview() {
    NegativeButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Text(
                text = "Button",
            )
        }
    )
}
