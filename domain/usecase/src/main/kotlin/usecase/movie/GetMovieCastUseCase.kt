package usecase.movie

import com.berlin.entity.Actor
import repository.MovieDetailsRepository

class GetMovieCastUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<Actor> =
        movieDetailsRepository.getMovieActors(movieId)
}