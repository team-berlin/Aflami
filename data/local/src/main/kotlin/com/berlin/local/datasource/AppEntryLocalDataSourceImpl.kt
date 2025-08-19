package com.berlin.local.datasource

import com.berlin.local.dao.AppEntryDao
import com.berlin.repository.datasource.local.datasource.AppEntryLocalDataSource
import com.berlin.repository.datasource.local.dto.AppEntryEntity
import javax.inject.Inject

class AppEntryLocalDataSourceImpl @Inject constructor(
    private val appEntryDao: AppEntryDao
) : AppEntryLocalDataSource {

    override suspend fun getAppEntry(): AppEntryEntity? {
        return appEntryDao.getAppEntry()
    }

    override suspend fun insertAppEntry(appEntry: AppEntryEntity) {
        appEntryDao.insertAppEntry(appEntry)
    }
}