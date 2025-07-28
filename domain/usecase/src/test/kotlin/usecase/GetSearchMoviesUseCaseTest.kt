//package usecase
//import com.berlin.entity.Movie
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import com.google.common.truth.Truth.assertThat
//import io.mockk.coVerify
//import kotlinx.datetime.LocalDate
//import org.junit.jupiter.api.assertThrows
//import usecase.movie.GetSearchMoviesUseCase
//
//class GetSearchMoviesUseCaseTest {
//    private val searchRepository: SearchRepository = mockk()
//    private lateinit var getSearchMoviesUseCase: GetSearchMoviesUseCase
//    @Before
//    fun setUp() {
//        getSearchMoviesUseCase = GetSearchMoviesUseCase(searchRepository)
//    }
//
//    @Test
//    fun `should return movies when repository returns result`() = runTest {
//        // Given
//        val query = "inception"
//        val language = "en-US"
//        val movies = listOf(
//            Movie(1, "Inception", 7.5, LocalDate(2020, 1, 1), emptyList(), "img"),
//            Movie(2, "Interstellar", 8.0, LocalDate(2021, 1, 1), emptyList(), "img"),
//        )
//        coEvery { searchRepository.searchMovie(query, language) } returns movies
//
//        // When
//        val result = getSearchMoviesUseCase(query, language)
//
//        // Then
//        assertThat(result).isEqualTo(movies)
//        coVerify(exactly = 1) { searchRepository.searchMovie(query, language) }
//    }
//
//    @Test
//    fun `should return empty list when repository returns nothing`() = runTest {
//        // Given
//        val query = "test"
//        val language = "en-US"
//        coEvery { searchRepository.searchMovie(query, language) } returns emptyList()
//
//        // When
//        val result = getSearchMoviesUseCase(query, language)
//
//        // Then
//        assertThat(result).isEmpty()
//        coVerify(exactly = 1) { searchRepository.searchMovie(query, language) }
//    }
//
//    @Test
//    fun `should throw exception when repository throws`() = runTest {
//        // Given
//        val query = "error"
//        val language = "en-US"
//        val exception = RuntimeException("Network error")
//        coEvery { searchRepository.searchMovie(query, language) } throws exception
//
//        // When
//        val thrown = assertThrows<RuntimeException> {
//            getSearchMoviesUseCase(query, language)
//        }
//
//        // Then
//        assertThat(thrown).hasMessageThat().isEqualTo("Network error")
//        coVerify(exactly = 1) { searchRepository.searchMovie(query, language) }
//    }
//}
//
