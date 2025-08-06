package repository

interface AppEntryRepository {
    suspend fun saveFirstEntry()
    suspend fun isFirstEntry(): Boolean
}