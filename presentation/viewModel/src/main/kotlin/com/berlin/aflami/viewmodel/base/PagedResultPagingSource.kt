package com.berlin.aflami.viewmodel.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.berlin.entity.PaginatedResult

abstract class PagedResultPagingSource<T : Any> : PagingSource<Int, T>() {

    abstract suspend fun fetchPage(page: Int): PaginatedResult<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = try {
        val page = params.key ?: 1
        val res = fetchPage(page)

        val prev = if (page > 1) page - 1 else null
        val next = if (page < res.totalPages) page + 1 else null
        LoadResult.Page(
            data = res.results,
            prevKey = prev,
            nextKey = next
        )
    } catch (t: Throwable) {
        LoadResult.Error(t)
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}