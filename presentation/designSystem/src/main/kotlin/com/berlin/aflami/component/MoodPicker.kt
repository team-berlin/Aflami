package com.berlin.aflami.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.designsystem.R

@Composable
fun MoodPicker(
    modifier: Modifier = Modifier,
    selectedMood: Int? = null,
    onMoodSelected: (Int) -> Unit = {},
    onGetNowClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.dp,
                color = Theme.color.stroke,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Theme.color.primary,
                        Theme.color.statusColors.redAccent,
                        Theme.color.statusColors.yellowAccent
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.clown),
            contentDescription = stringResource(R.string.mood_picker_clown_content_description),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(top = 8.dp)
                .height(121.dp)
                .align(Alignment.TopEnd)
        )
        MoodPickerHeader()
        MoodPickerContent(
            modifier = Modifier.padding(top = 76.dp, start = 2.dp, end = 2.dp, bottom = 2.dp),
            selectedMood = selectedMood,
            onMoodSelected = onMoodSelected,
            onGetNowClick = onGetNowClicked
        )
    }
}

@Composable
private fun MoodPickerHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        BlurredIcon()
        Text(
            text = stringResource(R.string.mood_picker_title),
            color = Theme.color.textColors.onPrimary,
            style = Theme.textStyle.label.medium,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
    }
}

@Composable
private fun MoodPickerContent(
    modifier: Modifier = Modifier,
    selectedMood: Int? = null,
    onMoodSelected: (Int) -> Unit,
    onGetNowClick: () -> Unit
) {
    val moodIcons = remember {
        listOf(
            R.drawable.ic_sad,
            R.drawable.ic_look_top,
            R.drawable.ic_love,
            R.drawable.ic_angry,
            R.drawable.ic_unhappy,
            R.drawable.ic_sad_dizzy
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Theme.color.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.mood_picker_prompt),
            color = Theme.color.textColors.body,
            style = Theme.textStyle.body.small,
            modifier = Modifier.padding(12.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            moodIcons.forEach { iconRes ->
                MoodIcon(
                    modifier = Modifier,
                    iconRes = iconRes,
                    isSelected = selectedMood == iconRes,
                    onClick = { onMoodSelected(iconRes) }
                )
            }
        }
        Text(
            text = stringResource(R.string.mood_picker_get_now),
            style = Theme.textStyle.body.medium,
            color = if (selectedMood != null) Theme.color.primary else Theme.color.disable,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 4.dp)
                .clickable(enabled = selectedMood != null, onClick = onGetNowClick)
        )
    }
}

@Composable
private fun MoodIcon(
    modifier: Modifier,
    iconRes: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = stringResource(R.string.mood_picker_mood_icon_content_description),
        tint = if (isSelected) Theme.color.primary else Theme.color.textColors.body,
        modifier = modifier
            .padding(4.dp)
            .clip(CircleShape)
            .size(24.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun BlurredIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 12.dp, top = 12.dp)
            .size(24.dp)
    ) {
        Box(
            modifier = modifier
                .matchParentSize()
                .background(color = Theme.color.onPrimaryButton, shape = CircleShape)
                .border(
                    width = 0.5.dp,
                    brush = Brush.linearGradient(colors = Theme.color.gradientColors.overly),
                    shape = CircleShape
                )
                .blur(8.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_favourite),
            contentDescription = stringResource(R.string.mood_picker_favourite_icon_content_description),
            tint = Theme.color.textColors.onPrimary,
            modifier = modifier
                .padding(4.dp)
                .size(16.dp)
        )
    }
}

@ThemeAndLocalePreviews
@Composable
private fun MoodMoodPickerPreview() {
    AflamiTheme {
        MoodPicker()
    }
}