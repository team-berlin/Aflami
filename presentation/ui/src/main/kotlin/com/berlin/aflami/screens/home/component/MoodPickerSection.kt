package com.berlin.aflami.screens.home.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.berlin.aflami.viewmodel.home.HomeInteractionListener
import com.berlin.aflami.viewmodel.home.HomeUiState
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.mapper.UserMood

@Composable
fun MoodPickerSection(
    state: HomeUiState, listener: HomeInteractionListener
) {
    val moodIcons = listOf(
        com.berlin.designsystem.R.drawable.ic_sad,
        com.berlin.designsystem.R.drawable.ic_look_top,
        com.berlin.designsystem.R.drawable.ic_love,
        com.berlin.designsystem.R.drawable.ic_angry,
        com.berlin.designsystem.R.drawable.ic_unhappy,
        com.berlin.designsystem.R.drawable.ic_sad_dizzy
    )

    val selectedMoodIcon = state.moodPickerUiState.selectedMood?.userMood?.let { mood ->
        moodIcons[MoodMapper.moodToIndex(mood)]
    }

    MoodPicker(
        moodIcons = moodIcons,
        headerText = stringResource(com.berlin.designsystem.R.string.mood_picker_title),
        promptText = stringResource(com.berlin.designsystem.R.string.mood_picker_prompt),
        actionText = stringResource(com.berlin.designsystem.R.string.mood_picker_get_now),
        imagePainter = painterResource(com.berlin.designsystem.R.drawable.clown),
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