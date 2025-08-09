package usecase.favouritelist

import repository.UserFavouriteListRepository

class EditListTitleUseCase(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(listId: Int, newListTitle: String) {
        userFavouriteListRepository.editListTitle(listId = listId, newListTitle = newListTitle)
    }
}