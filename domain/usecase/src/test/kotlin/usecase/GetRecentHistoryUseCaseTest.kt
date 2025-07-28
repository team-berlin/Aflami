//package usecase
//
//import com.google.common.truth.Truth.assertThat
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import usecase.search.GetRecentHistoryUseCase
//
//class GetRecentHistoryUseCaseTest {
//    private lateinit var repository: SearchRepository
//    private lateinit var useCase: GetRecentHistoryUseCase
//
//    @Before
//    fun setUp() {
//        repository = mockk()
//        useCase = GetRecentHistoryUseCase(repository)
//    }
//
//    @Test
//    fun `invoke should return recent search queries from repository`() = runTest {
//        // Given
//        val expectedQueries = listOf("Inception", "Matrix", "Oppenheimer")
//        coEvery { repository.getRecentSearchQueries() } returns expectedQueries
//
//        // When
//        val result = useCase()
//
//        // Then
//        assertThat(result).isEqualTo(expectedQueries)
//    }
//}