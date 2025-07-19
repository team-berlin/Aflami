package usecase

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import repository.SearchRepository

class ClearSearchHistoryUseCaseTest {
    private lateinit var repository: SearchRepository
    private lateinit var useCase: ClearSearchHistoryUseCase

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = ClearSearchHistoryUseCase(repository)
    }

    @Test
    fun `invoke should call clearSearchHistory on repository`() = runTest {
        // When
        useCase()

        // Then
        coVerify { repository.clearSearchHistory() }
    }
}