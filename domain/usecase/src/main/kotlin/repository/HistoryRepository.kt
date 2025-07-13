package repository

import java.util.concurrent.Flow

interface HistoryRepository {
suspend fun getSearchHistory(): Flow<List<SearchingHistory>>
}