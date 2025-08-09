package com.berlin.aflami.viewmodel.listFeature

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.berlin.aflami.viewmodel.base.BaseViewModel
import com.berlin.aflami.viewmodel.base.ErrorUiState
import com.berlin.aflami.viewmodel.reusableinteractionlistener.list.addTiList.FavouriteListItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import usecase.auth.GetLoginStatus
import usecase.favouritelist.CreateNewFavouriteListUseCase
import usecase.favouritelist.EditListTitleUseCase
import usecase.favouritelist.GetAllFavouriteListsUseCase
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val createNewFavouriteListUseCase: CreateNewFavouriteListUseCase,
    private val getAllFavouriteListsUseCase: GetAllFavouriteListsUseCase,
    private val getIsUserLoggedInUseCase: GetLoginStatus,
    private val editListTitleUseCase: EditListTitleUseCase,
    favouriteListArgs: FavouriteListArgs,
) : BaseViewModel<ListScreenState, ListScreenEffect>(ListScreenState()),
    ListScreenInteractionListener {

    val shouldShowEditSheet = favouriteListArgs.shouldShowEditSheet
    val requiredListIdToEdit = favouriteListArgs.requiredListIdToEdit
    val shouldShowDeleteSnackBar = favouriteListArgs.shouldShowDeleteSnackBar
    val isListDeletedSuccessfully = favouriteListArgs.isListDeletedSuccessfully

    val isLoggedIn: StateFlow<Boolean?> = getIsUserLoggedInUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )

    init {
        shouldShowEditSheet?.let {
            Log.d("Khairy", "showEditSheet ??? $shouldShowEditSheet and id = $requiredListIdToEdit")
            updateState { screenState ->
                screenState.copy(
                    editListSheetState = screenState.editListSheetState.copy(
                        isSaveButtonEnabled = true, requiredListIdToEdit = requiredListIdToEdit
                    )
                )
            }
        }
        shouldShowDeleteSnackBar?.let {
            Log.d(
                "Khairy",
                "showDeleteSheet ??? $shouldShowDeleteSnackBar and isDeleted = $isListDeletedSuccessfully"
            )
            sendNewEffect(
                ListScreenEffect.ShowListDeletedSnackBar(
                    isListDeletedSuccessfully!!
                )
            )
        }
        observeLoginStatus()
    }

    private fun observeLoginStatus() {
        viewModelScope.launch {
            isLoggedIn.collect { loggedIn ->
                Log.d("Khairy", "is user logged in ??? $loggedIn")
                updateState { screenState ->
                    if (loggedIn == true) getAllUserFavouriteLists()
                    screenState.copy(
                        isUserLoggedIn = loggedIn,
                        isLoginRequiredDialogVisible = if (loggedIn == false) true else false,
                        isScreenLoading = loggedIn ?: true
                    )
                }
            }
        }
    }

    //region getUserFavouriteLists
    private fun getAllUserFavouriteLists() {
        updateState { screenState -> screenState.copy(isScreenLoading = true) }
        Log.d("khairy", "getting all user fav lists ")
        tryToCall(
            call = { getAllFavouriteListsAsFlow().cachedIn(viewModelScope) },
            onSuccess = ::updateScreenStateWithUserFavouriteLists,
            onError = ::updateScreenStateWithErrorMessage,
        )
    }

    private fun getAllFavouriteListsAsFlow(): Flow<PagingData<FavouriteListItemUiState>> = Pager(
        config = defaultPageConfigurations(), pagingSourceFactory = {
            AllFavouriteListsPagingSource(getAllFavouriteListsUseCase)
        }).flow

    private fun updateScreenStateWithUserFavouriteLists(userFavouriteLists: Flow<PagingData<FavouriteListItemUiState>>) {
        updateState { screenState ->
            screenState.copy(
                favouriteList = userFavouriteLists, isScreenLoading = false
            )
        }.also {
            Log.d("khairy", "update screen state with new list $userFavouriteLists")
        }
    }

    private fun updateScreenStateWithErrorMessage(errorUiState: ErrorUiState) {
        updateState { screenState ->
            screenState.copy(
                errorMessage = errorUiState.message, isScreenLoading = false
            )
        }.also {
            Log.d("khairy", "failed $errorUiState")
        }
    }
    //endregion

    //region ListScreenInteractionListener sendNewEffects
    override fun onBackClicked() = sendNewEffect(ListScreenEffect.NavigateBack)

    override fun onListNameChange(newListTitle: TextFieldValue) {
        updateState { screenState ->
            screenState.copy(
                createNewListSheetState = screenState.createNewListSheetState.copy(
                    newListTitle = newListTitle.text
                )
            )
        }
    }

    override fun onLoginClicked() = sendNewEffect(ListScreenEffect.NavigateToLoginScreen)

    override fun onClickAddList() {
        updateState { screenState ->
            screenState.copy(
                createNewListSheetState = screenState.createNewListSheetState.copy(
                    isCreateNewListDialogVisible = true
                )
            )
        }
    }

//    override fun onCreateNewListClicked() =
//        updateState { screenState ->
//            screenState.copy(
//                createNewListSheetState = screenState.createNewListSheetState.copy(
//                    isCreateNewListDialogVisible = true
//                )
//            )
//        }

    override fun onClickListCard(listId: Int, listName: String) =
        sendNewEffect(ListScreenEffect.NavigateToSeeAllListScreen(listId, listName))

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

    override fun onUpdateNewListTitle(newListTitle: String) = updateState { screenState ->
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

    override fun onCancelEditingListClicked() = updateState { screenState ->
        screenState.copy(
            editListSheetState = screenState.editListSheetState.copy(
                isEditNewListDialogVisible = false,
            )
        )
    }

    override fun onOldListTitleChanged(editedListTitle: String) = updateState { screenState ->
        screenState.copy(editListSheetState = screenState.editListSheetState.copy(currentListTitle = editedListTitle))
    }

    override fun onSaveOldListTitleToNewTitleClicked(listId: Int, editedListTitle: String) {
        tryToCall(
            call = { editListTitleUseCase(listId = listId, newListTitle = editedListTitle) },
            onSuccess = {},
            onError = {})
    }


}