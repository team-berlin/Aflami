package usecase.movie

import com.berlin.entity.Movie
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(id: Long): Movie =
        movieDetailsRepository.getMovieDetails(id)
}