package usecase

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.SearchRepository

class SaveRecentHistoryUseCaseTest {

    private lateinit var repository: SearchRepository
    private lateinit var useCase: SaveRecentHistoryUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = SaveRecentHistoryUseCase(repository)
    }

    @Test
    fun `invoke should call saveRecentHistory on repository`() = runTest {
        // Given
        val query = "Inception"

        // When
        useCase(query)

        // Then
        coVerify { repository.saveRecentHistory(query) }
    }
}