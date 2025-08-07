package com.berlin.aflami.screens.home.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.berlin.aflami.screens.home.component.MoodPicker
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.home.HomeScreenInteractionListener
import com.berlin.aflami.viewmodel.home.HomeScreenState
import com.berlin.aflami.viewmodel.home.HomeScreenViewModel
import com.berlin.aflami.viewmodel.mapper.UserMood
import com.berlin.designsystem.R

@Composable
fun MoodPickerSection(
    modifier: Modifier = Modifier, state: HomeScreenState, listener: HomeScreenInteractionListener
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

    val selectedMoodIcon = remember(state.moodPickerUiState.selectedMood) {
        state.moodPickerUiState.selectedMood?.userMood?.let { mood ->
            moodIcons[MoodMapper.moodToIndex(mood)]
        }
    }



    MoodPicker(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(Theme.color.surface),
        moodIcons = moodIcons,
        headerText = stringResource(R.string.mood_picker_title),
        promptText = stringResource(R.string.mood_picker_prompt),
        actionText = stringResource(R.string.mood_picker_get_now),
        imagePainter = painterResource(com.berlin.ui.R.drawable.clown),
        selectedMood = selectedMoodIcon,
        viewModel = listener as HomeScreenViewModel,
     )
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