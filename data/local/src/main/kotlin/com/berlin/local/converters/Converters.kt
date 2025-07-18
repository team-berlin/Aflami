package com.berlin.local.converters

import androidx.room.TypeConverter
import com.berlin.repository.datasource.local.dto.QueryType

class Converters {
    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toIntList(data: String): List<Int> {
        return if (data.isEmpty()) emptyList()
        else data.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromSearchType(queryType: QueryType): String = queryType.name

    @TypeConverter
    fun toSearchType(name: String): QueryType = QueryType.valueOf(name)
}