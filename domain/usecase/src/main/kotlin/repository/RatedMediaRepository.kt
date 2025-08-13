package repository

import com.berlin.entity.PaginatedResult
import com.berlin.entity.RatedMovie
import com.berlin.entity.RatedTVShow

interface RatedMediaRepository {
    suspend fun getRatedMovies(page: Int): PaginatedResult<RatedMovie>
    suspend fun getRatedTVShows(page: Int): PaginatedResult<RatedTVShow>
}