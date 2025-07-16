package usecase

import repository.MovieDetailsRepository
import repository.SeriesDetailsRepository

class GetSeriesGallery(private val movieDetailsRepository: MovieDetailsRepository) {
    suspend operator fun invoke(id: Long): List<String> =
        movieDetailsRepository.getMovieImages(movieId = id)
}