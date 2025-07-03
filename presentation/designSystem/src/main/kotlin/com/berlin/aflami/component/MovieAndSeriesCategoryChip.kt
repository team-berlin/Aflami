package com.berlin.aflami.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun MovieCategoryChip(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    label: String,
    icon: Int,
    iconSize: Dp = 24.dp,
    isSelected: Boolean = false,
    selectedBackgroundColor: Color = Theme.color.secondary,
    unselectedBackgroundColor: Color = Theme.color.surfaceHigh,
    unselectedLabelColor: Color = Theme.color.textColors.hint,
    selectedLabelColor: Color = Theme.color.textColors.body,
    labelSize: TextUnit =Theme.textStyle.label.small.fontSize,
    labelColor: Color = Theme.color.textColors.hint,
    chipSize: Dp = 56.dp,
    onClick: () -> Unit = {}
){
    val clampedChipSize = chipSize.coerceIn(56.dp, 80.dp)
    val clampedIconSize = iconSize.coerceIn(24.dp, 40.dp)
    val clampedTextSize = labelSize.value.coerceIn(10f, 24f).sp
    Column(
        modifier = modifier
            .wrapContentWidth()
            .padding(horizontal = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Box(
            modifier = Modifier
                .size(clampedChipSize)
                .clip(shape = RoundedCornerShape(cornerRadius))
                .background(
                    color = if(isSelected) selectedBackgroundColor else unselectedBackgroundColor,
                )
                .clickable { onClick() }
                .then(
                    if (isSelected) Modifier.border(
                        1.dp,
                        Theme.color.stroke,
                        RoundedCornerShape(cornerRadius)
                    ) else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Category",
                modifier = Modifier.size(clampedIconSize),
                tint = if(isSelected) Theme.color.textColors.onPrimary else labelColor)
        }

        Text(
            modifier = Modifier.widthIn(max = clampedChipSize),
            text = label,
            color = if(isSelected) selectedLabelColor else unselectedLabelColor,
            style = Theme.textStyle.label.small,
            fontSize = clampedTextSize,
            textAlign = TextAlign.Center,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}



@ThemeAndLocalePreviews
@Composable
fun MovieCategoryChipPreview() {
    AflamiTheme {
        MovieCategoryChip(
            label = "Science Fiction",
            icon = R.drawable.action,
            isSelected = true
        )
    }
}