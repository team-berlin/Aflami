package com.berlin.local.datasource

import com.berlin.local.dao.GenrePreferencesDao
import com.berlin.repository.datasource.local.datasource.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import javax.inject.Inject

class CategoriesPreferencesDataSourceImpl @Inject constructor (
    private val genrePreferencesDao: GenrePreferencesDao
): CategoriesPreferencesDataSource

{
    override suspend fun insertOrUpdateCategoryScore(categoryId: Int, score: Int) {
        genrePreferencesDao.insert(CategoriesPreferencesEntity(categoryId, score))
    }

    override suspend fun getAllCategoryScores(): Map<Int, Int> {
        return genrePreferencesDao.getAll().associate { it.categoryId to it.count }
    }

    override suspend fun getCategoryScoreById(categoryId: Int): Int? {
        return genrePreferencesDao.getCount(categoryId)
    }

}