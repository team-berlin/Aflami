package usecase.tvshow

import com.berlin.entity.Genre
import repository.TVShowDetailsRepository

class GetTVShowGenresUseCase(
    private val repository: TVShowDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = repository.getTVShowGenres()
}