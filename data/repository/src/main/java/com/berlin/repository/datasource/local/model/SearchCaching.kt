package com.berlin.repository.datasource.local.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berlin.entity.MediaTypeEntity
import kotlinx.datetime.LocalDate

@Entity(tableName = "search_cache")
data class SearchCaching(
    @PrimaryKey val query: String,
    val type : String,
    val time : Long,
     val id: Long,
     val title: String,
     val rating: Double,
     val releaseYear: String,
     val description: String,
     val genre:List<Int>,
     val poster:String,
)
