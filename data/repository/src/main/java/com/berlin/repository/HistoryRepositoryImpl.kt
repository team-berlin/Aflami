package com.berlin.repository

import com.berlin.repository.datasource.local.SearchHistoryDataSource
import com.berlin.repository.datasource.local.SearchLocalDataSource


class SearchHistoryRepository(
    private val searchHistoryDataSource : SearchHistoryDataSource
    ) {

}

