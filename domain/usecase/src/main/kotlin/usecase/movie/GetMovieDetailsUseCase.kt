package usecase.movie

import com.berlin.entity.Movie
import repository.MovieDetailsRepository

class GetMovieDetailsUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): Movie =
        movieDetailsRepository.getMovieDetails(id)
}