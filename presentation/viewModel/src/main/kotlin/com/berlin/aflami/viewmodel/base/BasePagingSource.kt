package com.berlin.aflami.viewmodel.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any>(
//    private val call: suspend (page: Int) -> List<T>,
    private val pageNumber: Int = 1,
) : PagingSource<Int, T>() {

    abstract suspend fun fetchData(page: Int): List<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            val data = fetchData(page)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(pageNumber - 1) ?: page?.nextKey?.minus(pageNumber - 1)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}