package com.berlin.repository.datasource


import com.berlin.repository.datasource.local.datasource.AppEntryLocalDataSource
import com.berlin.repository.datasource.local.dto.AppEntryEntity
import repository.AppEntryRepository
import javax.inject.Inject

class AppEntryRepositoryImpl @Inject constructor(
    private val localDataSource: AppEntryLocalDataSource
) : AppEntryRepository {
    override suspend fun saveFirstEntry() {
        localDataSource.insertAppEntry(AppEntryEntity())
    }

    override suspend fun isFirstEntry(): Boolean {
        return localDataSource.getAppEntry() == null
    }

}
