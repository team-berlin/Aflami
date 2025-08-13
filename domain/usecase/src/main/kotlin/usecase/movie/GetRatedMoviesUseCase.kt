package usecase.movie

import com.berlin.entity.PaginatedResult
import com.berlin.entity.RatedMovie
import repository.RatedMediaRepository
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val ratedMediaRepository: RatedMediaRepository
) {
    suspend operator fun invoke(page: Int): PaginatedResult<RatedMovie> {
        return ratedMediaRepository.getRatedMovies(page)
    }
}