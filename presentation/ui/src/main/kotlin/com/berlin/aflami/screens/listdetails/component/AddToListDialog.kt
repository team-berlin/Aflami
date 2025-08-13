package com.berlin.aflami.screens.listdetails.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.berlin.aflami.component.CircularProgressIndicator
import com.berlin.aflami.component.IconButton
import com.berlin.aflami.component.buttons.SecondaryButton
import com.berlin.aflami.screens.NoInternetConnectionPlaceholder
import com.berlin.aflami.screens.lists.component.Dialog
import com.berlin.aflami.ui.color.ExtraColors.darkPurpleLinearGradient
import com.berlin.aflami.ui.theme.Theme
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.AddToListSheetState
import com.berlin.ui.R

@Composable
fun AddToListDialog(
    modifier: Modifier = Modifier,
    movieId: Long,
    onSelectedListChange: (favouriteListID: Int) -> Unit,
    onAddToSelectedList: (movieId: Long, listId: Int) -> Unit,
    onCreateNewList: () -> Unit = {},
    onDismiss: () -> Unit = {},
    addToListUiState: AddToListSheetState,
) {
    Dialog(
        onDismiss = onDismiss,
        modifier = modifier,
    ) {
        Box() {
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = addToListUiState.isLoading && addToListUiState.errorMessage.isNullOrEmpty()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    text = stringResource(R.string.loading)
                )
            }
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = addToListUiState.errorMessage != null && addToListUiState.isLoading.not()
            ) {
                NoInternetConnectionPlaceholder(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
            AnimatedVisibility(
                enter = fadeIn(),
                exit = fadeOut(),
                visible = addToListUiState.isLoading.not() && addToListUiState.errorMessage == null
            ) {
                val favouriteLists = addToListUiState.favouriteLists.collectAsLazyPagingItems()
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(12.dp),
                ) {
                    DialogHeaderSection(
                        onDismiss = onDismiss,
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .nestedScroll(rememberNestedScrollInteropConnection()),
                    ) {
                        items(favouriteLists.itemCount) { index ->
                            favouriteLists[index].let { favouriteList ->
                                SelectionListItem(
                                    listName = favouriteList!!.listTitle,
                                    itemCount = favouriteList.numberOfFavouriteMovies,
                                    isSelected = addToListUiState.selectedListId == favouriteList.listId,
                                    onSelectItem = {
                                        onSelectedListChange(favouriteList.listId!!)
                                    })
                            }
                        }
                    }
                    ActionButtonsSection(
                        movieId = movieId,
                        selectedListId = addToListUiState.selectedListId,
                        onAddToSelectedList = onAddToSelectedList,
                        onCreateNewList = onCreateNewList,
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogHeaderSection(onDismiss: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.add_to_list),
            style = Theme.textStyle.title.large,
            color = Theme.color.textColors.title,
        )

        IconButton(
            painter = painterResource(com.berlin.designsystem.R.drawable.cancel),
            contentDescription = null,
            tint = Theme.color.textColors.title,
            onClick = onDismiss,
        )
    }
}

@Composable
private fun SelectionListItem(
    listName: String,
    itemCount: Int,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelectItem: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else Theme.color.stroke,
                shape = RoundedCornerShape(16.dp),
            )
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Theme.color.primaryVariant else Theme.color.surface)
            .clickable(onClick = onSelectItem)
            .padding(horizontal = 12.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(vertical = 7.dp),
        ) {
            Text(
                text = listName,
                style = Theme.textStyle.title.large,
                color = Theme.color.textColors.body,
            )
            val context = LocalContext.current
            val itemCount =
                context.resources.getQuantityString(R.plurals.item_count, itemCount, itemCount)
            Text(
                text = itemCount,
                style = Theme.textStyle.label.small,
                color = Theme.color.textColors.hint,
            )
        }
        val icon = if (isSelected) {
            R.drawable.checkmark_list
        } else {
            R.drawable.add_list
        }

        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isSelected) Theme.color.primary else Theme.color.textColors.hint,
        )
    }
}

@Composable
private fun ActionButtonsSection(
    modifier: Modifier = Modifier,
    movieId: Long? = null,
    selectedListId: Int? = null,
    onAddToSelectedList: (movieId: Long, listId: Int) -> Unit,
    onCreateNewList: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        val isEnabled = selectedListId != null
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    brush = (if (isEnabled) darkPurpleLinearGradient
                    else Brush.linearGradient(listOf(Theme.color.disable, Theme.color.disable))),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(enabled = isEnabled) {
                    onAddToSelectedList(
                        movieId ?: throw IllegalStateException("no Movie Id found"),
                        selectedListId ?: throw IllegalStateException("no list selected")
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                stringResource(R.string.add),
                style = Theme.textStyle.label.large,
                color = if (isEnabled) Theme.color.textColors.onPrimary else Theme.color.stroke,
            )
        }

        SecondaryButton(
            onClick = onCreateNewList,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                text = stringResource(R.string.create_new_list),
                style = Theme.textStyle.label.large.copy(
                    textAlign = TextAlign.Center
                ),
                color = Theme.color.primary
            )
        }
    }
}