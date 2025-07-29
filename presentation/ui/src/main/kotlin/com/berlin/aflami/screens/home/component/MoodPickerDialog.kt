package com.berlin.aflami.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.berlin.aflami.component.MediaCard
import com.berlin.aflami.ui.color.ExtraColors.primaryGredient
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.shareduistate.MovieUIState
import com.berlin.ui.R

@Composable
fun MoodPickerDialog(
    onDismiss: () -> Unit,
    onClickViewDetails: () -> Unit,
    onClickGetAnotherMovie: () -> Unit,
    mediaImg: String,
    typeOfMedia: String,
    rate: String,
    date: String,
    title: String,
    modifier: Modifier = Modifier,
) {

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier.background(
                color = Theme.color.surface, shape = RoundedCornerShape(24.dp)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .width(328.dp)
//                    .height(468.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mood Picker",
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title,
                    )
                    IconButton(
                        onClick = onDismiss, modifier = Modifier.background(
                            Theme.color.surfaceHigh, shape = RoundedCornerShape(12.dp)
                        )
                    ) {
                        Icon(
                            painterResource(R.drawable.cancel_01),
                            contentDescription = null,
                            tint = Theme.color.textColors.title
                        )
                    }
                }
                Text(
                    text = "Movie that matches your mood is:",
                    style = Theme.textStyle.body.medium,
                    color = Theme.color.textColors.body,
                )
                MediaCard(
                    Modifier
                        .size(width = 304.dp, height = 196.dp)
                        .padding(top = 12.dp, bottom = 24.dp),
                    mediaImg = mediaImg,
                    title = title,
                    typeOfMedia = typeOfMedia,
                    date = date,
                    rating = rate,
                )
                Box(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            primaryGredient, shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            onClickViewDetails()
                        },
                    contentAlignment = Alignment.Center,
                ) {

                    Text(
                        text = "View details",
                        color = Theme.color.textColors.onPrimary,
                        style = Theme.textStyle.label.large,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            Theme.color.primaryVariant, shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            onClickGetAnotherMovie()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Get another movie",
                        color = Theme.color.primary,
                        style = Theme.textStyle.label.large,
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun MoodPickerDialogPreview() {
    AflamiTheme {
        MoodPickerDialog(
            onDismiss = {},
            mediaImg = "https://i.pinimg.com/736x/2d/4c/77/2d4c7718ccdf3d714654dbd3d66da00f.jpg",
            title = "Grave of the Fireflies",
            typeOfMedia = "TV show",
            date = "2016",
            rate = "9.9",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 166.dp),
            onClickViewDetails = {},
            onClickGetAnotherMovie = {},

        )
    }
}

@Composable
@Preview
fun MoodPickerDialogPreview2() {
    AflamiTheme(isDarkTheme = true) {
        MoodPickerDialog(
            onDismiss = {},
            mediaImg = "https://i.pinimg.com/736x/2d/4c/77/2d4c7718ccdf3d714654dbd3d66da00f.jpg",
            title = "Grave of the Fireflies",
            typeOfMedia = "TV show",
            date = "2016",
            rate = "9.9",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 166.dp),
            onClickViewDetails = {},
            onClickGetAnotherMovie = {},
        )
    }
}