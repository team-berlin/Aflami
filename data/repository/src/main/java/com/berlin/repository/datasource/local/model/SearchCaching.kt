import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_cache")
data class SearchCaching(
    @PrimaryKey val query: String,
    val history : String,
    val time : Long,
)
