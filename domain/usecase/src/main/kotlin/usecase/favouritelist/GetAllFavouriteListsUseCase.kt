package usecase.favouritelist

import com.berlin.entity.FavouriteList
import repository.UserFavouriteListRepository
import javax.inject.Inject

class GetAllFavouriteListsUseCase @Inject constructor(
    private val userFavouriteListRepository: UserFavouriteListRepository,
) {
    suspend operator fun invoke(): List<FavouriteList> =
        userFavouriteListRepository.getUserFavouriteLists()
}