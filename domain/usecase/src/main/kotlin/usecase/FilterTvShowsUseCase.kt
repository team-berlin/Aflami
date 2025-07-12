package usecase

import com.berlin.entity.TVShow
import repository.SearchRepository

class FilterTvShowsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        query: String,
        minRating: Float,
        genres: List<Int>
    ): List<TVShow> {
        val tvShows = searchRepository.searchTvShow(query)

        return tvShows.filter { tvShow ->
            val meetsRatingCriteria = tvShow.rating >= minRating
            val meetsGenreCriteria = if (genres.isEmpty() || genres.contains(1)) {
                true
            } else {
                tvShow.genre.any { genre -> genres.contains(genre) }
            }
            meetsRatingCriteria && meetsGenreCriteria
        }
    }
}