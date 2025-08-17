package com.berlin.aflami.viewmodel.listDetails

//data class ListDetailsScreenState()
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.paging.PagingData
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import com.berlin.aflami.viewmodel.shareduistate.MovieUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class ListDetailsScreenState(
    val listTitle: String = "",
    val listId: Int? = null,
    val numberOfFavouriteMovies: Int = 0,
    val listItemUiState: FavouriteListItemUiState = FavouriteListItemUiState(),
    val listItems: Flow<PagingData<MovieUiState>> = emptyFlow(),
    val showDeleteListDialog: Boolean = false,
    val isScreenLoading: Boolean = false,
    val errorMessage: String? = null,
)
