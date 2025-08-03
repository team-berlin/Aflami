package usecase.movie

import com.berlin.entity.ContinueWatchingMoviesModel
import com.berlin.entity.Movie
import repository.MovieRepository

class AddContinueWatchingMovieUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: ContinueWatchingMoviesModel) = repository.addContinueWatchingMovie(movie)
}