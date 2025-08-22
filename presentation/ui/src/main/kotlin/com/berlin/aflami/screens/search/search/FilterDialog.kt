package com.berlin.aflami.screens.search.search

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.berlin.aflami.ui.theme.AflamiTheme
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.search.FilterInteractionListener
import com.berlin.aflami.viewmodel.search.FilterMediaSelected
import com.berlin.designsystem.R

@Composable
fun FilterDialog(
    filterListener: FilterInteractionListener,
    state: FilterMediaSelected,
    getIcon: (Int) -> Int,
    getGenreName: (Int) -> Int
) {
    val isApplyEnabled = state.selectedRating > 0f || state.genreUiStates.any { it.isSelected&&it.name!="All"}

    val containerColor=if(isApplyEnabled)Theme.color.primary else Theme.color.disable
    val gradientColor=if(isApplyEnabled)Theme.color.primaryButton else Theme.color.disable
    val textColor=if(isApplyEnabled)Theme.color.textColors.onPrimary else Theme.color.stroke

    Dialog(
        onDismissRequest = filterListener::onCancelClicked,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Theme.color.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.filter_result),
                        style = Theme.textStyle.title.large,
                        color = Theme.color.textColors.title
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Theme.color.surfaceHigh)
                            .clickable { filterListener.onCancelClicked() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.cancel),
                            contentDescription = stringResource(R.string.icon_cd),
                            modifier = Modifier.size(24.dp),
                            tint = Theme.color.textColors.title
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = stringResource(R.string.imdb_rating),
                        style = Theme.textStyle.title.small,
                        color = Theme.color.textColors.title
                    )

                    RatingBar(
                        modifier = Modifier,
                        onValueChange = { filterListener.onRatingStarChanged(it) },
                        currentRating = state.selectedRating
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = stringResource(R.string.Genre),
                        style = Theme.textStyle.title.small,
                        color = Theme.color.textColors.title
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = state.genreUiStates
                        ) { filterGenre ->
                            Chips(
                                title = stringResource(getGenreName(filterGenre.id)),
                                icon = painterResource(
                                    getIcon(filterGenre.id)
                                ),
                                isSelected = filterGenre.isSelected,
                                onClick = { filterListener.onFilterGenreChanged(filterGenre.id) }
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PrimaryButton(
                        onClick ={if(isApplyEnabled) { filterListener.onApplyButtonClicked() }},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        containerColor = containerColor,
                        gradientColor = gradientColor
                    ) {
                        Text(
                            stringResource(com.berlin.ui.R.string.apply),
                            style = Theme.textStyle.label.large,
                            color = textColor
                        )
                    }
                    PrimaryButton(
                        onClick = { filterListener.onClearButtonClicked() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        containerColor = Theme.color.primaryVariant
                    ) {
                        Text(
                            stringResource(com.berlin.ui.R.string.clear),
                            style = Theme.textStyle.label.large,
                            color = Theme.color.primary
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Chips(
    title: String, icon: Painter, isSelected: Boolean, onClick: () -> Unit,
    minimumTextLines: Int = 1
) {
    val background by animateColorAsState(
        targetValue = if (isSelected) Theme.color.secondary else Theme.color.surfaceHigh
    )

    val border by animateColorAsState(
        targetValue = if (isSelected) Theme.color.stroke else Color.Transparent
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.textColors.onPrimary else Theme.color.textColors.hint
    )

    val isSingleWord = remember(title) { title.trim().split("\\s+".toRegex()).size == 1 }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(background)
                .border(
                    width = 1.dp, shape = RoundedCornerShape(16.dp), color = border
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = icon,
                contentDescription = stringResource(id = R.string.icon_cd),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(iconColor)
            )
        }

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .let {
                    if (!isSingleWord) it.width(56.dp) else it
                },
            text = title,
            color = Theme.color.textColors.body,
            style = Theme.textStyle.label.small,
            textAlign = TextAlign.Center,
            minLines = minimumTextLines.coerceIn(
                minimumValue = 1,
                maximumValue = 2
            ),
            maxLines = if (isSingleWord && minimumTextLines == 1) 1 else 2
        )
    }
}

@Composable
fun RatingBar(
    onValueChange: (Float) -> Unit, modifier: Modifier = Modifier, currentRating: Float = 0f
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..10) {
            Icon(
                painter = painterResource(
                    id = if (i <= currentRating)
                        R.drawable.ic_star_filled
                    else
                        R.drawable.ic_star_outline
                ),
                contentDescription = null,
                tint = Theme.color.statusColors.yellowAccent,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onValueChange(i.toFloat()) }
            )
            if (i < 10) Spacer(modifier = Modifier.width(6.dp))
        }
    }
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Magenta,
    gradientColor: Color? = null,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = if (gradientColor != null) listOf(
                        containerColor, gradientColor
                    ) else listOf(
                        containerColor, containerColor
                    )
                )
            )
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterDialogPreview() {
    AflamiTheme {

    }
}


