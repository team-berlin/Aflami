package repository

interface SeriesDetailsRepository {
    suspend fun getSeriesImages(id:Long):List<String>

}