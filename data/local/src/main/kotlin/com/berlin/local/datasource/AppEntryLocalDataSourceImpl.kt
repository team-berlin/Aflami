package com.berlin.local.datasource

import com.berlin.local.dao.AppEntryDao
import com.berlin.repository.datasource.local.AppEntryLocalDataSource
import com.berlin.repository.datasource.local.dto.AppEntryEntity

class AppEntryLocalDataSourceImpl(
    private val appEntryDao: AppEntryDao
) : AppEntryLocalDataSource {

    override suspend fun getAppEntry(): AppEntryEntity? {
        return appEntryDao.getAppEntry()
    }

    override suspend fun insertAppEntry(appEntry: AppEntryEntity) {
        appEntryDao.insertAppEntry(appEntry)
    }


}