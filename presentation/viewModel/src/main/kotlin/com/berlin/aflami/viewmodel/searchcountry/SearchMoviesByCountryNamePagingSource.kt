package com.berlin.aflami.viewmodel.searchcountry

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.aflami.viewmodel.util.getCountryIsoCode
import com.berlin.entity.Movie
import usecase.movie.SearchMoviesByCountryUseCase

class SearchMoviesByCountryNamePagingSource(
    private val countryName: String,
    private val searchMoviesByCountryUseCase: SearchMoviesByCountryUseCase,
) : BasePagingSource<Movie>() {
    override suspend fun fetchData(page: Int): List<Movie> =
        searchMoviesByCountryUseCase(query = getCountryIsoCode(countryName) ?: "EG", page)
}