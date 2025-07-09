import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SearchByActorViewModel(
//private val searchByActorUseCase: SearchByActorUseCase,

) :
    ViewModel() {

    private val _searchByActorUiState = MutableStateFlow(SearchByActorUiState())
    val searchByActorUiState = _searchByActorUiState
}

fun onSearchQueryChange(query: String) {

}

data class SearchByActorUiState(
    val searchQuery: String = "",
    val searchResults: List<MediaUiState> = emptyList()

)

data class MediaUiState(
    val id: Long = 0L,
    val title: String = "",
    val mediaType: String = "",
    val rating: String = "0.0",
    val releaseYear: String = "",
    val description: String = "",
    val genre: List<Int> = emptyList(),
    val poster: String = ""
)
