package usecase.favouritelist

import repository.UserFavouriteListRepository
import javax.inject.Inject

class DeleteMovieFromUserFavouriteListUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(listId: Int, movieId: Long) =
        userFavouriteListRepository.deleteMovieFromUserFavouriteList(listId, movieId)
}