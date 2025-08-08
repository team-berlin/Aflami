package repository

import com.berlin.entity.PaginatedResult
import com.berlin.entity.RatedMedia

interface RatedMediaRepository {
    suspend fun getRatedMovies(page: Int): PaginatedResult<RatedMedia>
    suspend fun getRatedTVShows(page: Int): PaginatedResult<RatedMedia>
}