package usecase.tvshow

import com.berlin.entity.Genre
import repository.TVShowDetailsRepository
import javax.inject.Inject

class GetTVShowGenresUseCase @Inject constructor(
    private val tvShowDetailsRepository: TVShowDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = tvShowDetailsRepository.getTVShowsGenres()
}