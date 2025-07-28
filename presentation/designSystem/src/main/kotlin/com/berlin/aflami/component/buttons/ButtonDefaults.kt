package com.berlin.aflami.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.Theme

data class ButtonColors(
    val backgroundColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val backgroundGradient: Brush? = null,
    val disabledBackgroundColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val errorBackgroundColor: Color = Color.Unspecified,
    val errorContentColor: Color = Color.Unspecified,
)

object ButtonDefaults {
    val defaultShape: RoundedCornerShape = RoundedCornerShape(16.dp)
    val defaultPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    val defaultFabPadding: PaddingValues = PaddingValues(horizontal = 18.dp, vertical = 18.dp)

    @Composable
    fun colors() = ButtonColors(
        backgroundColor = Theme.color.primary,
        contentColor = Theme.color.textColors.onPrimary,
        disabledBackgroundColor = Theme.color.disable,
        disabledContentColor = Theme.color.stroke,
        errorBackgroundColor = Theme.color.statusColors.redVariant,
        errorContentColor = Theme.color.statusColors.redAccent,
    )
}