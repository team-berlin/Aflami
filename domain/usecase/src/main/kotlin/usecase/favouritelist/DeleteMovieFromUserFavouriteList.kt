package usecase.favouritelist

import repository.UserFavouriteListRepository

class DeleteMovieFromUserFavouriteList(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(listId: Int, movieId: Long) =
        userFavouriteListRepository.deleteMovieFromUserFavouriteList(listId, movieId)
}