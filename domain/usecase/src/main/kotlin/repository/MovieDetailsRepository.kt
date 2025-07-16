package repository

interface MovieDetailsRepository {
    suspend fun getMovieImages(movieId: Long): List<String>
}