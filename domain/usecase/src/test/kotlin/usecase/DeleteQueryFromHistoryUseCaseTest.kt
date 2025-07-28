//package usecase
//
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import usecase.search.DeleteQueryFromHistoryUseCase
//
//
//class DeleteQueryFromHistoryUseCaseTest {
//    private lateinit var repository: SearchRepository
//    private lateinit var useCase: DeleteQueryFromHistoryUseCase
//
//    @Before
//    fun setUp() {
//        repository = mockk(relaxed = true)
//        useCase = DeleteQueryFromHistoryUseCase(repository)
//    }
//
//    @Test
//    fun `invoke should call deleteQueryFromHistory on repository`() = runTest {
//        // Given
//        val query = "Matrix"
//
//        // When
//        useCase(query)
//
//        // Then
//        coVerify { repository.deleteQueryFromHistory(query) }
//    }
//}