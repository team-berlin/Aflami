package com.berlin.aflami.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme

@Composable
fun GenersChip(
    label: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean=false,
    selectedLabelColor: Color = Theme.color.textColors.onPrimary,
    unselectedLabelColor: Color = Theme.color.primary,
    selectedBackgroundColor: Color = Theme.color.primary,
    unselectedBackgroundColor: Color = Theme.color.surfaceHigh,
    labelSize: TextUnit = Theme.textStyle.label.small.fontSize,
    cornerRadius: Dp = 8.dp,
    isClickable: Boolean = false,
    onClick: () -> Unit = {}
){
    val clampedTextSize = labelSize.value.coerceIn(10f, 24f).sp

    Surface(
        modifier = modifier.wrapContentSize(),
        color = if(isSelected) selectedBackgroundColor else unselectedBackgroundColor,
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(
            modifier = Modifier
                .then(if (isClickable) Modifier.clickable { onClick() } else Modifier)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                color = if(isSelected) selectedLabelColor else unselectedLabelColor,
                style = Theme.textStyle.label.small.copy(fontSize = clampedTextSize),
                textAlign = TextAlign.Center,
                maxLines =1
            )
        }
    }
}



@ThemeAndLocalePreviews
@Composable
fun GenersChipPreview(){
    AflamiTheme {
        GenersChip(label = "Drama")
    }
}