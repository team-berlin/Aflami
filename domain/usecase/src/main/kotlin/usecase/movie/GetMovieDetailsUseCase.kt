package usecase.movie

import com.berlin.entity.Movie
import repository.MovieDetailsRepository

class GetMovieDetailsUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): Movie =
        repository.getMovieDetails(id)
}