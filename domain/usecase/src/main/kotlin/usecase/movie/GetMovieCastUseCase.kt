package usecase.movie

import com.berlin.entity.Actor
import repository.MovieDetailsRepository

class GetMovieCastUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<Actor> =
        repository.getMovieActors(movieId)
}