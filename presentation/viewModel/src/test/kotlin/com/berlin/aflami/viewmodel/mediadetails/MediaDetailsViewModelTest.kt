package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.mediadetails.uistate.CompanyProductionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.RowSectionUiState
import com.berlin.aflami.viewmodel.mediadetails.uistate.TabContent
import com.berlin.aflami.viewmodel.mediadetails.uistate.UiText
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.entity.Episode
import com.berlin.entity.Movie
import com.berlin.entity.ProductionCompany
import com.berlin.entity.Review
import com.berlin.entity.TvShowDetails
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import usecase.mediadetails.GetMovieCastUseCase
import usecase.mediadetails.GetMovieDetailsUseCase
import usecase.mediadetails.GetMovieGalleryUseCase
import usecase.mediadetails.GetMovieReviewUseCase
import usecase.mediadetails.GetSeasonEpisodesUseCase
import usecase.mediadetails.GetSeriesCastUseCase
import usecase.mediadetails.GetSeriesGalleryUseCase
import usecase.mediadetails.GetSeriesReviewUseCase
import usecase.mediadetails.GetSimilarMoviesUseCase
import usecase.mediadetails.GetSimilarSeriesUseCase
import usecase.mediadetails.GetTvShowDetailsUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class MediaDetailsViewModelTest {
    private lateinit var viewModel: MediaDetailsViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var getTvShowDetailsUseCase: GetTvShowDetailsUseCase
    private lateinit var getMovieCastUseCase: GetMovieCastUseCase
    private lateinit var getSeriesCastUseCase: GetSeriesCastUseCase
    private lateinit var getMovieGalleryUseCase: GetMovieGalleryUseCase
    private lateinit var getSeriesGalleryUseCase: GetSeriesGalleryUseCase
    private lateinit var getSimilarMoviesUseCase: GetSimilarMoviesUseCase
    private lateinit var getSimilarSeriesUseCase: GetSimilarSeriesUseCase
    private lateinit var getMovieReviewUseCase: GetMovieReviewUseCase
    private lateinit var getSeriesReviewUseCase: GetSeriesReviewUseCase
    private lateinit var getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        mockkStatic(android.util.Log::class)
        every { android.util.Log.v(any(), any()) } returns 0
        every { android.util.Log.d(any(), any()) } returns 0
        every { android.util.Log.i(any(), any()) } returns 0
        every { android.util.Log.e(any(), any()) } returns 0
        every { android.util.Log.e(any(), any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)

        savedStateHandle = mockk(relaxed = true)
        getMovieDetailsUseCase = mockk(relaxed = true)
        getTvShowDetailsUseCase = mockk(relaxed = true)
        getMovieCastUseCase = mockk(relaxed = true)
        getSeriesCastUseCase = mockk(relaxed = true)
        getMovieGalleryUseCase = mockk(relaxed = true)
        getSeriesGalleryUseCase = mockk(relaxed = true)
        getSimilarMoviesUseCase = mockk(relaxed = true)
        getSimilarSeriesUseCase = mockk(relaxed = true)
        getMovieReviewUseCase = mockk(relaxed = true)
        getSeriesReviewUseCase = mockk(relaxed = true)
        getSeasonEpisodesUseCase = mockk(relaxed = true)

        every { savedStateHandle.get<String>("id") } returns "1"
        every { savedStateHandle.get<String>("media_type") } returns MediaType.MOVIE.name

        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        viewModel = MediaDetailsViewModel(
            savedStateHandle,
            getMovieDetailsUseCase,
            getTvShowDetailsUseCase,
            getMovieCastUseCase,
            getSeriesCastUseCase,
            getMovieGalleryUseCase,
            getSeriesGalleryUseCase,
            getSimilarMoviesUseCase,
            getSimilarSeriesUseCase,
            getMovieReviewUseCase,
            getSeriesReviewUseCase,
            getSeasonEpisodesUseCase
        )
    }

    @Test
    fun `getMediaDetails for movie should update state with movie details`() = runTest {
        // Given
        val movie = MovieDetails(
            id = 1,
            title = "Test Movie",
            overview = "A test movie",
            posterUrl = "poster.jpg",
            backdropUrl = "backdrop.jpg",
            genres = listOf(GenreEntity(1, "Action")),
            releaseDate = "2023-01-01",
            rating = 20.0,
            runtime = 120,
            productionCompanies = listOf(ProductionCompany(1, "Test Studio", null, null)),
            hasVideo = false,
            originCountry = "US",
            duration = null
        )

        coEvery { getMovieDetailsUseCase(1, any()) } returns movie

        // When
        viewModel.getMediaDetails(1, MediaType.MOVIE, "en")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.id).isEqualTo(1)
        assertThat(state.title).isEqualTo("Test Movie")
        assertThat(state.overview).isEqualTo("A test movie")
        assertThat(state.posterUrl).isEqualTo("poster.jpg")
        assertThat(state.backdropUrl).isEqualTo("backdrop.jpg")
        assertThat(state.releaseYear).isEqualTo("2023-01-01")
        assertThat(state.rating).isEqualTo(20.0)
        assertThat(state.runtime).isEqualTo("2h 0m")
        assertThat(state.genres).containsExactly("Action")
        assertThat(state.hasVideo).isFalse()
        assertThat(state.originalCountry).isEqualTo("US")
        assertThat(state.duration).isNull()
        assertThat(viewModel.companyProductionCache).isNotNull()
        assertThat(viewModel.companyProductionCache!!.first().id).isEqualTo("1")
        assertThat(viewModel.companyProductionCache!!.first().name).isEqualTo("Test Studio")
    }

    @Test
    fun `getMediaDetails for tv show should update state with tv show details`() = runTest {
        // Given
        val tvShow = TvShowDetails(
            id = 2,
            title = "Test Show",
            overview = "A test TV show",
            posterUrl = "poster.jpg",
            backdropUrl = "backdrop.jpg",
            genres = listOf(GenreEntity(2, "Drama")),
            releaseDate = "2022-01-01",
            rating = 7.5,
            runtime = 45,
            productionCompanies = listOf(ProductionCompany(2, "Test Network", null, null)),
            seasons = emptyList(),
            numberOfSeasons = 3,
            originCountry = "US",
        )
        coEvery { getTvShowDetailsUseCase(2, any()) } returns tvShow
        every { savedStateHandle.get<String>("id") } returns "2"
        every { savedStateHandle.get<String>("media_type") } returns MediaType.TVSHOW.name
        viewModel = MediaDetailsViewModel(
            savedStateHandle,
            getMovieDetailsUseCase,
            getTvShowDetailsUseCase,
            getMovieCastUseCase,
            getSeriesCastUseCase,
            getMovieGalleryUseCase,
            getSeriesGalleryUseCase,
            getSimilarMoviesUseCase,
            getSimilarSeriesUseCase,
            getMovieReviewUseCase,
            getSeriesReviewUseCase,
            getSeasonEpisodesUseCase
        )

        // When
        viewModel.getMediaDetails(2, MediaType.TVSHOW, "en")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.id).isEqualTo(2)
        assertThat(state.title).isEqualTo("Test Show")
        assertThat(state.overview).isEqualTo("A test TV show")
        assertThat(state.posterUrl).isEqualTo("poster.jpg")
        assertThat(state.backdropUrl).isEqualTo("backdrop.jpg")
        assertThat(state.releaseYear).isEqualTo("2022-01-01") // just the year, not full date!
        assertThat(state.rating).isEqualTo(7.5)
        assertThat(state.runtime).isEmpty() // always "" for TV shows
        assertThat(state.genres).containsExactly("Drama")
        assertThat(state.numberOfSeasons).isEqualTo(3)
        assertThat(state.originalCountry).isEqualTo("US") // fix here!
        assertThat(viewModel.companyProductionCache).isNotNull()
        assertThat(viewModel.companyProductionCache!![0].id).isEqualTo("2")
        assertThat(viewModel.companyProductionCache!![0].name).isEqualTo("Test Network")
    }

    @Test
    fun `getMediaDetails with error should update state with error message`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getMovieDetailsUseCase(1, any()) } throws RuntimeException(errorMessage)

        // When
        viewModel.getMediaDetails(1, MediaType.MOVIE, "en")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value

        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEqualTo(UiText.Dynamic(errorMessage)) // ✅ Correct check

        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Error::class.java)
        val err = state.rowSection as RowSectionUiState.Error
        assertThat(err.message).isEqualTo(errorMessage) // ✅ Still valid, because you store String in Error.message
    }

    @Test
    fun `onPlayClicked should update isPlaying and send PlayMedia effect`() = runTest {
        // Given
        val effects = mutableListOf<MediaDetailsScreenEffect>()
        val job = launch { viewModel.effect.collect { effects.add(it) } }

        // When
        viewModel.onPlayClicked(1)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isPlaying).isTrue()
        assertThat(effects).containsExactly(MediaDetailsScreenEffect.PlayMedia(id = 1))
        job.cancel()
    }

    @Test
    fun `onShowReviewsClicked for movie with reviews should update state with reviews`() = runTest {
        // Given
        val review = Review(
            id = "1",
            name = "Amr",
            userName = "Amr123",
            avatarImage = "avatar.jpg",
            rating = 5.0,
            content = "Test review",
            date = "2023-01-01T12:00:00Z"
        )
        coEvery { getMovieReviewUseCase(1) } returns listOf(review)

        // When
        viewModel.onShowReviewsClicked(1, MediaType.MOVIE)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
        val successState = state.rowSection as RowSectionUiState.Success
        assertThat(successState.content).isInstanceOf(TabContent.Reviews::class.java)
        val reviewsContent = successState.content as TabContent.Reviews
        assertThat(reviewsContent.items).containsExactly(review.toUiState())
    }

    @Test
    fun `onShowReviewsClicked with no reviews should update state with no data found`() = runTest {
        // Given
        coEvery { getMovieReviewUseCase(1) } returns emptyList()

        // When
        viewModel.onShowReviewsClicked(1, MediaType.MOVIE)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.NoDataFound::class.java)

        val noDataState = state.rowSection as RowSectionUiState.NoDataFound
        assertThat(noDataState.message).isEqualTo(UiText.Resource(MediaDetailsViewModel.NO_REVIEWS))
    }

    @Test
    fun `onShowMediaGalleryClicked with gallery should update state with gallery`() = runTest {
        // Given
        val gallery = listOf("image1.jpg", "image2.jpg")
        coEvery { getMovieGalleryUseCase(1) } returns gallery

        // When
        viewModel.onShowMediaGalleryClicked(1, MediaType.MOVIE)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
        val successState = state.rowSection as RowSectionUiState.Success
        assertThat(successState.content).isInstanceOf(TabContent.Gallery::class.java)
        val galleryContent = successState.content as TabContent.Gallery
        assertThat(galleryContent.items).isEqualTo(gallery)
    }

    @Test
    fun `onShowMediaGalleryClicked with empty gallery should update state with no data found`() =
        runTest {
            // Given
            coEvery { getMovieGalleryUseCase(1) } returns emptyList()

            // When
            viewModel.onShowMediaGalleryClicked(1, MediaType.MOVIE)
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value
            assertThat(state.rowSection).isInstanceOf(RowSectionUiState.NoDataFound::class.java)

            val noDataState = state.rowSection as RowSectionUiState.NoDataFound
            assertThat(noDataState.message).isEqualTo(UiText.Resource(MediaDetailsViewModel.NO_GALLERY))
        }

    @Test
    fun `onShowCompanyProductionClicked should update state with cached companies`() = runTest {
        // Given
        val company = mockk<CompanyProductionUiState> {
            every { id } returns 1.toString()
            every { name } returns "Test Studio"
        }
        viewModel.companyProductionCache = listOf(company)

        // When
        viewModel.onShowCompanyProductionClicked()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
        val successState = state.rowSection as RowSectionUiState.Success
        assertThat(successState.content).isInstanceOf(TabContent.CompanyProduction::class.java)
        val companyContent = successState.content as TabContent.CompanyProduction
        assertThat(companyContent.items).isEqualTo(listOf(company))
    }

    @Test
    fun `onSeasonsClicked with episodes should update state with season episodes`() = runTest {
        // Given
        val episode = Episode(
            episodeId = 1,
            name = "Episode 1",
            description = "Episode overview",
            episodeNumber = 1,
            airDate = null,
            episodeType = null,
            duration = null,
            rating = null,
            stillPath = null
        )
        coEvery { getSeasonEpisodesUseCase(1, 0) } returns listOf(episode)

        // When
        viewModel.onSeasonsClicked(1, 1)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
        val successState = state.rowSection as RowSectionUiState.Success
        assertThat(successState.content).isInstanceOf(TabContent.Season::class.java)
        val seasonContent = successState.content as TabContent.Season
        assertThat(seasonContent.items[0]).isEqualTo(listOf(episode.toUiState()))
    }

    @Test
    fun `onSeasonsClicked with error should update state with error message`() = runTest {
        // Given
        val errorMessage = "Failed to load episodes"
        coEvery { getSeasonEpisodesUseCase(1, 0) } throws RuntimeException(errorMessage)
        // When
        viewModel.onSeasonsClicked(1, 1)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Error::class.java)
        val errorState = state.rowSection as RowSectionUiState.Error
        assertThat(errorState.message).isEqualTo(errorMessage)
    }

    @Test
    fun `toggleMovieDetailsTab to MORE_LIKE_THIS should trigger onShowMoreMediaLikeThisClicked`() =
        runTest {
            // Given
            val movie = Movie(
                id = 55,
                title = "Matrix",
                rating = 8.8,
                genres = listOf(28, 12, 878),
                releaseDate = LocalDate.parse("1999-03-31"),
                poster = "poster.png"
            )
            val mappedUiState = movie.toUIStateMedia()
            coEvery { getSimilarMoviesUseCase(1) } returns listOf(movie)

            // When
            viewModel.toggleMovieDetailsTab(MovieDetailsTabs.MORE_LIKE_THIS, 1, MediaType.MOVIE)
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value
            assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
            val successState = state.rowSection as RowSectionUiState.Success
            assertThat(successState.content).isInstanceOf(TabContent.MoreLikeThis::class.java)
            val moreLikeThisContent = successState.content as TabContent.MoreLikeThis
            assertThat(moreLikeThisContent.items).containsExactly(mappedUiState)
        }

    @Test
    fun `onBackClicked should send NavigateBack effect`() = runTest {
        // Given
        val effects = mutableListOf<MediaDetailsScreenEffect>()
        val job = launch { viewModel.effect.collect { effects.add(it) } }

        // When
        viewModel.onBackClicked()
        advanceUntilIdle()

        // Then
        assertThat(effects).containsExactly(MediaDetailsScreenEffect.NavigateBack)
        job.cancel()
    }

//    @Test
//    fun `onRateIconClicked should send ShowRatingSheet effect`() = runTest {
//        // Given
//        val effects = mutableListOf<MediaDetailsScreenEffect>()
//        val job = launch { viewModel.effect.collect { effects.add(it) } }
//
//        // When
//        viewModel.onRateIconClicked(1)
//        advanceUntilIdle()
//
//        // Then
//        assertThat(effects).containsExactly(MediaDetailsScreenEffect.ShowRatingDialog(id = 1))
//        job.cancel()
//    }

    @Test
    fun `onRateIconClicked when not logged in should show login dialog`() = runTest {
        viewModel.onRateIconClicked(1)
        runCurrent()
        assertThat(viewModel.showLoginRequiredDialog.value).isTrue()
    }

//    @Test
//    fun `onAddMediaToFavouriteListClicked should send ShowAddToFavoriteListSheet effect`() =
//        runTest {
//            // Given
//            val effects = mutableListOf<MediaDetailsScreenEffect>()
//            val job = launch { viewModel.effect.collect { effects.add(it) } }
//
//            // When
//            viewModel.onAddMediaToFavouriteListClicked(1, 2)
//            advanceUntilIdle()
//
//            // Then
//            assertThat(effects).containsExactly(
//                MediaDetailsScreenEffect.ShowAddToFavoriteListDialog(
//                    favouriteListId = 1,
//                    mediaId = 2
//                )
//            )
//            job.cancel()
//        }

    @Test
    fun `onAddToListIconClicked when not logged in should show login dialog`() = runTest {
        viewModel.onAddMediaToFavouriteListClicked(1,1)
        runCurrent()
        assertThat(viewModel.showLoginRequiredDialog.value).isTrue()
    }

    @Test
    fun `onReadMoreDescriptionClicked should toggle expanded state`() = runTest {

        // Given&When
        viewModel.onReadMoreDescriptionClicked()
        advanceUntilIdle()
    }

    @Test
    fun `toggleMovieDetailsTab to GALLERY should load gallery content`() = runTest {
        // Given
        val gallery = listOf("image1.png")
        coEvery { getMovieGalleryUseCase(1) } returns gallery

        // When
        viewModel.toggleMovieDetailsTab(MovieDetailsTabs.GALLERY, 1, MediaType.MOVIE)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
        val successState = state.rowSection as RowSectionUiState.Success
        val content = successState.content as TabContent.Gallery
        assertThat(content.items).containsExactly("image1.png")
    }

    @Test
    fun `toggleMovieDetailsTab to REVIEWS should load reviews`() = runTest {
        coEvery { getMovieReviewUseCase(1) } returns emptyList()
        viewModel.toggleMovieDetailsTab(MovieDetailsTabs.REVIEWS, 1, MediaType.MOVIE)
        advanceUntilIdle()
        val rowSection = viewModel.state.value.rowSection
        assertThat(rowSection).isInstanceOf(RowSectionUiState.NoDataFound::class.java)
    }

    @Test
    fun `toggleMovieDetailsTab to GALLERY should load gallery`() = runTest {
        coEvery { getMovieGalleryUseCase(1) } returns listOf("gallery1.jpg")
        viewModel.toggleMovieDetailsTab(MovieDetailsTabs.GALLERY, 1, MediaType.MOVIE)
        advanceUntilIdle()
        val content = (viewModel.state.value.rowSection as RowSectionUiState.Success).content
        assertThat((content as TabContent.Gallery).items).containsExactly("gallery1.jpg")
    }

    @Test
    fun `toggleMovieDetailsTab to COMPANY_PRODUCTION with cache should load companies`() = runTest {
        val company = CompanyProductionUiState("17", "logo.png", "Dreamworks", "US")
        viewModel.companyProductionCache = listOf(company)
        viewModel.toggleMovieDetailsTab(MovieDetailsTabs.COMPANY_PRODUCTION, 1, MediaType.MOVIE)
        advanceUntilIdle()
        val section = viewModel.state.value.rowSection as RowSectionUiState.Success
        val companies = (section.content as TabContent.CompanyProduction).items
        assertThat(companies).containsExactly(company)
    }

    @Test
    fun `toggleMovieDetailsTab to SEASON should load seasons`() = runTest {
        // GIVEN
        val episode = Episode(
            episodeId = 1, name = "E1", description = "E1", episodeNumber = 1,
            airDate = null, episodeType = null, duration = null, rating = null, stillPath = null
        )
        coEvery { getSeasonEpisodesUseCase(1, 0) } returns listOf(episode)
        // Use a TV Show with numberOfSeasons = 1
        val tvShow = TvShowDetails(
            id = 1,
            title = "TV Show",
            overview = "X",
            posterUrl = "",
            backdropUrl = "",
            genres = emptyList(),
            releaseDate = "2020-01-01",
            rating = 0.0,
            runtime = 50,
            productionCompanies = emptyList(),
            seasons = emptyList(),
            numberOfSeasons = 1,
            originCountry = "US"
        )
        coEvery { getTvShowDetailsUseCase(1, any()) } returns tvShow
        every { savedStateHandle.get<String>("id") } returns "1"
        every { savedStateHandle.get<String>("media_type") } returns MediaType.TVSHOW.name
        viewModel = MediaDetailsViewModel(
            savedStateHandle,
            getMovieDetailsUseCase,
            getTvShowDetailsUseCase,
            getMovieCastUseCase,
            getSeriesCastUseCase,
            getMovieGalleryUseCase,
            getSeriesGalleryUseCase,
            getSimilarMoviesUseCase,
            getSimilarSeriesUseCase,
            getMovieReviewUseCase,
            getSeriesReviewUseCase,
            getSeasonEpisodesUseCase
        )

        viewModel.getMediaDetails(1, MediaType.TVSHOW, "en")
        advanceUntilIdle()

        // WHEN
        viewModel.toggleMovieDetailsTab(MovieDetailsTabs.SEASON, 1, MediaType.TVSHOW)
        advanceUntilIdle()

        // THEN
        val rowSection = viewModel.state.value.rowSection as RowSectionUiState.Success
        val seasonContent = rowSection.content as TabContent.Season
        assertThat(seasonContent.items[0]).isEqualTo(listOf(episode.toUiState()))
    }

    @Test
    fun `onShowReviewsClicked handles error`() = runTest {
        val errorMsg = "Oops"
        coEvery { getMovieReviewUseCase(1) } throws RuntimeException(errorMsg)
        viewModel.onShowReviewsClicked(1, MediaType.MOVIE)
        advanceUntilIdle()
        val section = viewModel.state.value.rowSection as RowSectionUiState.Error
        assertThat(section.message).isEqualTo(errorMsg)
    }

    @Test
    fun `onShowCompanyProductionClicked with empty cache shows no data`() = runTest {
        viewModel.companyProductionCache = emptyList()
        viewModel.onShowCompanyProductionClicked()
        advanceUntilIdle()
        assertThat(viewModel.state.value.rowSection)
            .isInstanceOf(RowSectionUiState.NoDataFound::class.java)
    }

    @Test
    fun `onShowMediaGalleryClicked handles error`() = runTest {
        val errorMsg = "gallery error"
        coEvery { getMovieGalleryUseCase(1) } throws RuntimeException(errorMsg)
        viewModel.onShowMediaGalleryClicked(1, MediaType.MOVIE)
        advanceUntilIdle()
        val err = viewModel.state.value.rowSection as RowSectionUiState.Error
        assertThat(err.message).isEqualTo(errorMsg)
    }

    @Test
    fun `onSeasonsClicked with multiple seasons adds all episodes`() = runTest {
        val episode1 = Episode(episodeId = 111, name = "E1", description = "", episodeNumber = 1, airDate = null, episodeType = null, duration = null, rating = null, stillPath = null)
        val episode2 = Episode(episodeId = 222, name = "E2", description = "", episodeNumber = 1, airDate = null, episodeType = null, duration = null, rating = null, stillPath = null)
        coEvery { getSeasonEpisodesUseCase(1, 0) } returns listOf(episode1)
        coEvery { getSeasonEpisodesUseCase(1, 1) } returns listOf(episode2)
        viewModel.onSeasonsClicked(1, 2)
        advanceUntilIdle()
        val content = (viewModel.state.value.rowSection as RowSectionUiState.Success).content as TabContent.Season
        assertThat(content.items[0]).isEqualTo(listOf(episode1.toUiState()))
        assertThat(content.items[1]).isEqualTo(listOf(episode2.toUiState()))
    }

    @Test
    fun `description expanded toggles`() = runTest {
        assertThat(viewModel.isDescriptionExpanded()).isFalse()

        viewModel.onReadMoreDescriptionClicked()
        advanceUntilIdle()
        assertThat(viewModel.isDescriptionExpanded()).isTrue()

        viewModel.onReadMoreDescriptionClicked()
        advanceUntilIdle()
        assertThat(viewModel.isDescriptionExpanded()).isFalse()
    }

    @Test
    fun `review expanded toggles by id`() = runTest {
        val id = 123L
        assertThat(viewModel.isReviewExpanded(id)).isFalse()

        viewModel.onReadMoreReviewClicked(id)
        advanceUntilIdle()
        assertThat(viewModel.isReviewExpanded(id)).isTrue()

        viewModel.onReadMoreReviewClicked(id)
        advanceUntilIdle()
        assertThat(viewModel.isReviewExpanded(id)).isFalse()
    }

    @Test fun `onShowCastClicked triggers navigation effect`() = runTest {
        val effects = mutableListOf<MediaDetailsScreenEffect>()
        val job = launch { viewModel.effect.collect { effects.add(it) } }
        viewModel.onShowCastClicked()
        advanceUntilIdle()
        assertThat(effects).containsExactly(MediaDetailsScreenEffect.NavigateToShowAllCastScreen(1, MediaType.MOVIE))
        job.cancel()
    }
}