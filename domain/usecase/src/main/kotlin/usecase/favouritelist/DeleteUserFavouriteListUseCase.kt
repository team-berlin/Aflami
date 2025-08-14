package usecase.favouritelist

import repository.UserFavouriteListRepository

class DeleteUserFavouriteListUseCase(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(listId: Int) =
        userFavouriteListRepository.deleteUserFavouriteList(listId)
}