package usecase.favouritelist

import repository.UserFavouriteListRepository
import javax.inject.Inject

class AddMovieToFavouriteListUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(movieId: Long, listId: Int) =
        userFavouriteListRepository.addMovieToFavouriteList(movieId, listId)
}