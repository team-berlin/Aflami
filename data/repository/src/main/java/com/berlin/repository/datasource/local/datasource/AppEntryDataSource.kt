package com.berlin.repository.datasource.local.datasource

import com.berlin.repository.datasource.local.dto.AppEntryEntity

interface AppEntryLocalDataSource{
    suspend fun getAppEntry(): AppEntryEntity?
    suspend fun insertAppEntry(appEntry: AppEntryEntity)
}