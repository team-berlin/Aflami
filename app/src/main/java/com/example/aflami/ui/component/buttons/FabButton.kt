package com.example.aflami.ui.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.example.aflami.designsystem.theme.Theme

@Composable
fun FabButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.IDLE,
    enabled: Boolean = state != ButtonState.DISABLED,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultFabPadding,
    buttonColors: ButtonColors = ButtonDefaults.colors(),
    content: @Composable RowScope.() -> Unit
) {
    val currentContent = if (state == ButtonState.LOADING) {{}}else content

    DefaultButton(
        onClick = onClick,
        modifier = modifier,
        state = state,
        enabled = enabled,
        isFabType = true,
        contentPadding = contentPadding,
        colors = buttonColors.copy(
            backgroundGradient = Brush.horizontalGradient(
                Theme.color.gradientColors.pointsOverly
            )
        ),
        shape = shape,
        content = currentContent
    )
}

@Preview
@Composable
private fun FabButtonPreview() {
    FabButton(
        onClick = {},
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
private fun FabButtonLoadingPreview() {
    FabButton(
        onClick = {},
        state = ButtonState.LOADING,
        content = {}
    )
}

@Preview
@Composable
private fun FabButtonDisabledPreview() {
    FabButton(
        onClick = {},
        state = ButtonState.DISABLED,
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    )
}

@Preview
@Composable
private fun FabButtonErrorPreview() {
    FabButton(
        onClick = {},
        state = ButtonState.ERROR,
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        })
}
