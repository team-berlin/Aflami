package com.berlin.aflami.viewmodel.listFeature

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.mapper.toFavouriteListUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.favouritelist.CreateNewFavouriteListUseCase
import usecase.favouritelist.GetAllFavouriteListsUseCase

class ListScreenViewModel(
    private val createNewFavouriteListUseCase: CreateNewFavouriteListUseCase,
    private val getAllFavouriteListsUseCase: GetAllFavouriteListsUseCase,
    private val getIsUserLoggedInUseCase: GetLoginStatus,
) : BaseViewModel<ListScreenState, ListScreenEffect>(ListScreenState()),
    ListScreenInteractionListener {
    val isLoggedIn: StateFlow<Boolean> = getIsUserLoggedInUseCase
        .observeLoginStatus()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        observeLoginStatus()
    }

    private fun observeLoginStatus() {
        viewModelScope.launch {
            isLoggedIn.collect { loggedIn ->
                updateState { screenState ->
                    if (loggedIn) getAllUserFavouriteLists()
                    screenState.copy(isUserLoggedIn = loggedIn)
                }
            }
        }
    }

    //region getUserFavouriteLists
    private fun getAllUserFavouriteLists() {
        tryToCall(
            call = { getAllFavouriteListsUseCase().map { favouriteList -> favouriteList.toFavouriteListUiState() } },
            onSuccess = ::updateScreenStateWithUserFavouriteLists,
            onError = ::updateScreenStateWithErrorMessage,
        )
    }

    private fun updateScreenStateWithUserFavouriteLists(userFavouriteLists: List<FavouriteListItemUiState>) {
        updateState { screenState -> screenState.copy(favouriteList = userFavouriteLists) }
    }

    private fun updateScreenStateWithErrorMessage(errorUiState: ErrorUiState) {
        updateState { screenState -> screenState.copy(errorMessage = errorUiState.message) }
    }
    //endregion

    //region ListScreenInteractionListener sendNewEffects
    override fun onBackClicked() = sendNewEffect(ListScreenEffect.NavigateBack)

    override fun onListNameChange(newListTitle: TextFieldValue) {
        TODO("Not yet implemented")
    }

    override fun onLoginClicked() = sendNewEffect(ListScreenEffect.NavigateToLoginScreen)

    override fun onClickAddList() {
        TODO("Not yet implemented")
    }

    override fun onCreateNewListClicked() =
        sendNewEffect(ListScreenEffect.ShowCreateNewListSheet)

    override fun onClickListCard(listId: Int, listName: String) =
        sendNewEffect(ListScreenEffect.NavigateToSeeAllListScreen(listId))

    override fun onCancelCreatingNewListClicked() {
        updateState { screenState ->
            screenState.copy(
                createNewListSheetState = screenState.createNewListSheetState.copy(
                    newListTitle = "",
                    isCreateNewListButtonEnabled = false,
                    isCreateNewListDialogVisible = false
                )
            )
        }
    }

    //endregion

    override fun onUpdateNewListTitle(newListTitle: String) =
        updateState { screenState ->
            screenState.copy(
                createNewListSheetState = screenState.createNewListSheetState.copy(
                    newListTitle = newListTitle
                )
            )
        }

    override fun onCreateNewListClicked(listTitle: String) {
        tryToCall(
            call = {
                createNewFavouriteListUseCase(listTitle)
            },
            onSuccess = {
                sendNewEffect(
                    ListScreenEffect.ShowCreateNewListStatusSnackBar(
                        isListCreatedSuccessfully = true
                    )
                )
            },
            onError = {
                sendNewEffect(
                    ListScreenEffect.ShowCreateNewListStatusSnackBar(
                        isListCreatedSuccessfully = false
                    )
                )
            },
        )
    }


}