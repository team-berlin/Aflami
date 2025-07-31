package com.berlin.repository.datasource.local

interface CategoriesPreferencesDataSource {

    suspend fun insertOrUpdateCategoryScore(categoryId: Int, score: Int)

    suspend fun getAllCategoryScores(): Map<Int, Int>

    suspend fun getCategoryScoreById(categoryId: Int): Int?
}