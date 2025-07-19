package com.berlin.local.datasource

import com.berlin.local.dao.CategoriesPreferencesDao
import com.berlin.repository.datasource.local.CategoriesPreferencesDataSource
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity

class CategoriesPreferencesDataSourceImpl (
    private val categoriesPreferencesDao: CategoriesPreferencesDao
): CategoriesPreferencesDataSource

{
    override suspend fun insertOrUpdateCategoryScore(categoryId: Int, score: Int) {
        categoriesPreferencesDao.insert(CategoriesPreferencesEntity(categoryId, score))
    }

    override suspend fun getAllCategoryScores(): Map<Int, Int> {
        return categoriesPreferencesDao.getAll().associate { it.categoryId to it.count }
    }

    override suspend fun getCategoryScoreById(categoryId: Int): Int? {
        return categoriesPreferencesDao.getCount(categoryId)
    }

}