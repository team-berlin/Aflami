package com.berlin.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity


@Dao
interface GenrePreferencesDao {

    @Query("SELECT count FROM GENRE_PREFERENCES WHERE categoryId = :id LIMIT 1")
    suspend fun getCount(id: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories:CategoriesPreferencesEntity)

    @Query("UPDATE GENRE_PREFERENCES SET count = count + 1 WHERE categoryId = :id")
    suspend fun increment(id: Int)

    @Query("SELECT * FROM GENRE_PREFERENCES")
    suspend fun getAll(): List<CategoriesPreferencesEntity>

}