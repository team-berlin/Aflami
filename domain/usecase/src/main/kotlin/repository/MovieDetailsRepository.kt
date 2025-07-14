package repository

import com.berlin.entity.MediaCast

interface MovieDetailsRepository {
    suspend fun getMovieCastDetails(movieId:Long):List<MediaCast>
}