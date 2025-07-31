package usecase

import com.berlin.entity.Genre
import repository.MovieDetailsRepository

class GetMovieGenresUseCase(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend operator fun invoke(): List<Genre> {
        return movieDetailsRepository.getMovieGenres()
    }
}

