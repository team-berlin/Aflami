package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity


@Dao
interface CategoriesPreferencesDao {

    @Query("SELECT count FROM categories_preferences WHERE categoryId = :id LIMIT 1")
    suspend fun getCount(id: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories:CategoriesPreferencesEntity)

    @Query("UPDATE categories_preferences SET count = count + 1 WHERE categoryId = :id")
    suspend fun increment(id: Int)

    @Query("SELECT * FROM categories_preferences")
    suspend fun getAll(): List<CategoriesPreferencesEntity>

}