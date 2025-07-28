//package usecase
//
//import com.berlin.entity.TVShow
//import com.google.common.truth.Truth.assertThat
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import kotlinx.datetime.LocalDate
//import org.junit.Before
//import org.junit.Test
//import org.junit.jupiter.api.assertThrows
//import usecase.tvshow.GetSearchTVShowsUseCase
//
//class GetSearchTvShowsUseCaseTest {
//
//
//    private lateinit var repository: SearchRepository
//    private lateinit var useCase: GetSearchTVShowsUseCase
//
//    @Before
//    fun setup() {
//        repository = mockk()
//        useCase = GetSearchTVShowsUseCase(repository)
//    }
//
//    @Test
//    fun `should return list of tv shows`() = runTest {
//        val query = "breaking"
//        val language = "en-US"
//        val expected = listOf(
//            TVShow(
//                1, "Breaking Bad",
//                7.5, LocalDate(2020, 1, 1), emptyList(), "img"
//            )
//        )
//
//        coEvery { repository.searchTVShow(query, language) } returns expected
//
//        val result = useCase(query, language)
//
//        assertThat(result).isEqualTo(expected)
//        coVerify { repository.searchTVShow(query, language) }
//    }
//
//    @Test
//    fun `should return empty list if repository returns nothing`() = runTest {
//        val query = "nothing"
//        val language = "en-US"
//        coEvery { repository.searchTVShow(query, language) } returns emptyList()
//
//        val result = useCase(query, language)
//
//        assertThat(result).isEmpty()
//    }
//
//    @Test
//    fun `should throw if repository throws`() = runTest {
//        val query = "fail"
//        val language = "en-US"
//        coEvery { repository.searchTVShow(query, language) } throws RuntimeException("error")
//
//        val exception = assertThrows<RuntimeException> {
//            useCase(query, language)
//        }
//
//        assertThat(exception).hasMessageThat().isEqualTo("error")
//    }
//}