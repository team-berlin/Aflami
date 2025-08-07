package usecase.movie

import com.berlin.entity.Movie
import repository.MovieRepository
import javax.inject.Inject

class GetMoviesByMoodUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genresIds: List<Int>): List<Movie> {
        return movieRepository.getMoviesByMoods(moods = genresIds)
    }
}