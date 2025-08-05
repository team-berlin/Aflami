package usecase.favouritelist

import com.berlin.entity.Movie
import repository.UserFavouriteListRepository
import javax.inject.Inject

class GetFavouriteListItemsUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(pageNumber: Int, favouriteListId: Int): List<Movie> {
        return userFavouriteListRepository.getUserFavouriteListItems(
            pageNumber = pageNumber,
            favouriteListId = favouriteListId,
        )
    }
}