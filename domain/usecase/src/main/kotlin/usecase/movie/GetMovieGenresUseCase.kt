package usecase.movie

import com.berlin.entity.Genre
import repository.MovieDetailsRepository

class GetMovieGenresUseCase(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = movieDetailsRepository.getMovieGenres()
}