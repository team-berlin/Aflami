package usecase.movie

import com.berlin.entity.Genre
import repository.MovieDetailsRepository

class GetMovieGenresUseCase(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = repository.getMovieGenres()
}