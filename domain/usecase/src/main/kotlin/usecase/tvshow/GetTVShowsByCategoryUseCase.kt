package usecase.tvshow

import com.berlin.entity.Movie
import repository.MovieRepository
import repository.TVShowRepository
import javax.inject.Inject

class GetTVShowsByCategoryUseCase @Inject constructor(
    private val tvShowRepository: TVShowRepository
) {
    suspend operator fun invoke(genreId: Long,page: Int) =
        tvShowRepository.getTVShowsByCategory(
            genreId = genreId,
            page = page
        )
}