package com.berlin.aflami.viewmodel.searchactor

import com.berlin.aflami.viewmodel.base.BasePagingSource
import com.berlin.entity.Movie
import usecase.movie.SearchByActorNameUseCase

class SearchByActorNamePagingSource(
    private val actorName: String,
    private val searchByActorNameUseCase: SearchByActorNameUseCase,
) : BasePagingSource<Movie>() {
    override suspend fun fetchData(page: Int): List<Movie> =
        searchByActorNameUseCase(actorName, page)
}