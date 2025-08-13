package usecase.favouritelist

import repository.UserFavouriteListRepository

class CreateNewFavouriteListUseCase(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(title: String): Int =
        userFavouriteListRepository.createNewFavouriteList(title)
}