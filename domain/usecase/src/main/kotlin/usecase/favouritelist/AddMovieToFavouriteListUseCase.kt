package usecase.favouritelist

import repository.UserFavouriteListRepository

class AddMovieToFavouriteListUseCase(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(movieId: Long, listId: Int) =
        userFavouriteListRepository.addMovieToFavouriteList(movieId, listId)
}