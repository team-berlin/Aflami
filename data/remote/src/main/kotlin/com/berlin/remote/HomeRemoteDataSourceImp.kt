//package com.berlin.remote
//
//import com.berlin.remote.network.HomeApiService
//import com.berlin.repository.datasource.remote.dto.MovieResponse
//import com.berlin.repository.datasource.remote.dto.TVShowResponse
//
//class HomeRemoteDataSourceImp(
//    private val homeApiService: HomeApiService
//) : HomeRemoteDataSource {
//    override suspend fun getPopularMovies(language: String): MovieResponse {
//        return homeApiService.popularMovies(language)
//    }
//
//    override suspend fun getPopularTVShows(language: String): TVShowResponse {
//        return homeApiService.popularTVShows(language)
//    }
//}