package usecase

import com.berlin.entity.Movie
import repository.MovieRepository

class GetMoviesByMoodUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genresIds: List<Int>): List<Movie> {
        return movieRepository.getMoviesByMoods(moods = genresIds)
    }
}