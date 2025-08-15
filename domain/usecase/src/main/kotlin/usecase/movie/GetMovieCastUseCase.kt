package usecase.movie

import com.berlin.entity.Actor
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Long): List<Actor> =
        movieDetailsRepository.getMovieActors(movieId)
}