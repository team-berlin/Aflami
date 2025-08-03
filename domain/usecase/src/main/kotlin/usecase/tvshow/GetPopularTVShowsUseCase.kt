package usecase.tvshow

import com.berlin.entity.TVShow
import repository.TVShowRepository
import javax.inject.Inject

class GetPopularTVShowsUseCase @Inject constructor(
    private val repository: TVShowRepository
) {
    suspend operator fun invoke(): List<TVShow> = repository.getPopularTVShows()
}