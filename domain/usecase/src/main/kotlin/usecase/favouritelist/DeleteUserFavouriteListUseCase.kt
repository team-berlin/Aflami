package usecase.favouritelist

import repository.UserFavouriteListRepository
import javax.inject.Inject

class DeleteUserFavouriteListUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(listId: Int) =
        userFavouriteListRepository.deleteUserFavouriteList(listId)
}