package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository
import javax.inject.Inject

class GetUpComingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreId:Long): List<Movie> = movieRepository.getUpComingMovies(genreId)
}