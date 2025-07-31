//package usecase
//
//import com.berlin.entity.TVShow
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import kotlinx.datetime.LocalDate
//import org.junit.Before
//import org.junit.Test
//import usecase.tvshow.GetTopRatedTVShowUseCase
//
//class GetTopRatedSeriesUseCaseTest {
//    private lateinit var homeRepository: HomeRepository
//    private lateinit var getTopRatedSeriesUseCase: GetTopRatedTVShowUseCase
//
//    @Before
//    fun setUp() {
//        homeRepository = mockk(relaxed = true)
//        getTopRatedSeriesUseCase = GetTopRatedTVShowUseCase(homeRepository)
//    }
//
//    @Test
//    fun `should call getTopRatedSeries in repository when useCase gets called`() = runTest {
//        // when
//        getTopRatedSeriesUseCase(1)
//
//        // then
//        coVerify(exactly = 1) { homeRepository.getTopRatedSeries(1) }
//    }
//
//    @Test(expected = RuntimeException::class)
//    fun `invoke should throw exception when repository throws`() = runTest {
//        // Given
//        val page = 1
//        coEvery { homeRepository.getTopRatedSeries(page) } throws RuntimeException("Error")
//
//        // When
//        getTopRatedSeriesUseCase(page)
//
//        // Then exception is thrown
//    }
//
//    @Test
//    fun `invoke should return empty list when repository returns empty list`() = runTest {
//        // Given
//        val page = 1
//        coEvery { homeRepository.getTopRatedSeries(page) } returns emptyList()
//
//        // When
//        val result = getTopRatedSeriesUseCase(page)
//
//        // Then
//        assert(result.isEmpty())
//    }
//
//    @Test
//    fun `invoke should return same list of tv shows that repository returned`() = runTest {
//        // Given
//        val page = 1
//        coEvery { homeRepository.getTopRatedSeries(page) } returns tvShows
//
//        // When
//        val result = getTopRatedSeriesUseCase(page)
//
//        // Then
//        assert(result == tvShows)
//    }
//
//    companion object {
//        val tvShows = listOf(
//            TVShow(
//                id = 1698,
//                title = "praesent",
//                rating = 6.7,
//                releaseYear = LocalDate(2025,1,1),
//                genre = listOf(),
//                poster = "quis",
//                backdropPath = "nulla",
//                overview = "hinc",
//                releaseDate = "potenti",
//                runtime = 2893
//
//            )
//        )
//    }
//}
