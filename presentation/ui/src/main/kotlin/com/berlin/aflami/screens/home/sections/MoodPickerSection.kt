package com.berlin.aflami.screens.home.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.berlin.aflami.screens.home.component.MoodPicker
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeUiState
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.designsystem.R

@Composable
fun MoodPickerSection(
    state: HomeUiState, listener: HomeInteractionListener
) {
    val moodIcons = listOf(
        R.drawable.ic_sad,
        R.drawable.ic_look_top,
        R.drawable.ic_love,
        R.drawable.ic_angry,
        R.drawable.ic_unhappy,
        R.drawable.ic_sad_dizzy
    )

    val selectedMoodIcon = state.moodPickerUiState.selectedMood?.userMood?.let { mood ->
        moodIcons[MoodMapper.moodToIndex(mood)]
    }

    MoodPicker(
        moodIcons = moodIcons,
        headerText = stringResource(R.string.mood_picker_title),
        promptText = stringResource(R.string.mood_picker_prompt),
        actionText = stringResource(R.string.mood_picker_get_now),
        imagePainter = painterResource(R.drawable.clown),
        selectedMood = selectedMoodIcon,
        viewModel = listener as HomeViewModel,
        onEffect = { })
}

private object MoodMapper {
    private val moodToIndexMap = mapOf(
        UserMood.SAD to 0,
        UserMood.NEUTRAL to 1,
        UserMood.ROMANTIC to 2,
        UserMood.ANGRY to 3,
        UserMood.DEPRESSED to 4,
        UserMood.SAD_DIZZY to 5
    )

    fun moodToIndex(mood: UserMood): Int = moodToIndexMap[mood] ?: 0
}