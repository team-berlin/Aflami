package usecase.movie

import com.berlin.entity.Genre
import repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(): List<Genre> = movieDetailsRepository.getMovieGenres()
}