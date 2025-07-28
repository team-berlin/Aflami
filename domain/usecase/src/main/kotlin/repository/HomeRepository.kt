package repository

import com.berlin.entity.Movie
import com.berlin.entity.TVShow

interface HomeRepository {
    suspend fun getTopRatedMovies(page:Int):List<Movie>
    suspend fun getTopRatedSeries(page:Int):List<TVShow>
}