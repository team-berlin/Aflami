//package usecase
//
//import com.berlin.entity.Movie
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runTest
//import kotlinx.datetime.LocalDate
//import org.junit.Before
//import org.junit.Test
//import usecase.movie.GetTopRatedMoviesUseCase
//
//class GetTopRatedMoviesTest {
//    private lateinit var homeRepository: HomeRepository
//    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
//
//    @Before
//    fun setUp() {
//        homeRepository = mockk(relaxed = true)
//        getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(homeRepository)
//    }
//
//    @Test
//    fun `should call getTopRatedMovies in repository when useCase gets called`() = runTest {
//        // when
//        getTopRatedMoviesUseCase(1)
//
//        // then
//        coVerify (exactly = 1){ homeRepository.getTopRatedMovies(1) }
//    }
//
//    @Test(expected = RuntimeException::class)
//    fun `invoke should throw exception when repository throws`() = runTest {
//        // Given
//        val page = 1
//        coEvery { homeRepository.getTopRatedMovies(page) } throws RuntimeException("Error")
//
//        // When
//        getTopRatedMoviesUseCase(page)
//
//        // Then exception is thrown
//    }
//
//    @Test
//    fun `invoke should return empty list when repository returns empty list`() = runTest {
//        // Given
//        val page = 1
//        coEvery { homeRepository.getTopRatedMovies(page) } returns emptyList()
//        // When
//        val result = getTopRatedMoviesUseCase(page)
//        // Then
//        assert(result.isEmpty())
//    }
//
//    @Test
//    fun `invoke should return same list of movies that repository returned it`() = runTest {
//        //Given
//        val page = 1
//        coEvery { getTopRatedMoviesUseCase(page) } returns movies
//
//        //When
//        val result = getTopRatedMoviesUseCase(page)
//
//        //Then
//        assert(result == movies)
//
//    }
//
//    companion object {
//        val movies = listOf(
//            Movie(
//                id = 5472,
//                title = "noster",
//                rating = 2.3,
//                releaseDate = LocalDate(2025, 1, 1),
//                genres = listOf(),
//                poster = "ceteros",
//                screenShot = "eu",
//                description = "tantas",
//                releaseDate = "facilisi",
//                duration = 6071
//            )
//        )
//    }
//}