package usecase.mediadetails

import com.berlin.entity.Actor
import repository.MovieDetailsRepository

class GetMovieCastUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long, language: String): List<Actor> {
        return movieDetailsRepository.getMovieActors(movieId, language)
    }
}