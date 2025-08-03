package com.berlin.repository.datasource


import androidx.room.TypeConverter

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
    fun fromStringList(list: List<String>?): String {
        return list?.joinToString("|||") ?: ""
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return if (data.isEmpty()) emptyList()
        else data.split("|||")
    }
}