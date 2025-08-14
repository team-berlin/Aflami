package usecase.favouritelist

import repository.UserFavouriteListRepository
import javax.inject.Inject

class CreateNewFavouriteListUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(title: String): Int =
        userFavouriteListRepository.createNewFavouriteList(title)
}