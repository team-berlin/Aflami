package usecase

import com.berlin.entity.Movie
import com.berlin.entity.MovieGenre
import repository.MovieRepository

class GetUpComingMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genre: MovieGenre): List<Movie> {
        return movieRepository.getUpComingMovies().let { movies ->
            if (genre == MovieGenre.ALL) movies
            else movies.filter { it.categories.contains(genre) }
        }
    }
}