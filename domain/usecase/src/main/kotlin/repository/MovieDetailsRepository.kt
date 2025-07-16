package repository

import com.berlin.entity.Movie

interface MovieDetailsRepository {
    suspend fun getMovieSimilar(movieId:Long):List<Movie>


}