package usecase.favouritelist

import repository.UserFavouriteListRepository
import javax.inject.Inject

class EditListTitleUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(listId: Int, newListTitle: String) {
        userFavouriteListRepository.editListTitle(listId = listId, newListTitle = newListTitle)
    }
}