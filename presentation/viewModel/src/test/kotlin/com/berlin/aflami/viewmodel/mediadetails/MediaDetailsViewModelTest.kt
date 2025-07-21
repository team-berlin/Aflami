package com.berlin.aflami.viewmodel.mediadetails

import androidx.lifecycle.SavedStateHandle
import com.berlin.aflami.viewmodel.mapper.toUIStateMedia
import com.berlin.aflami.viewmodel.mapper.toUiState
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsScreenEffect
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.mediadetails.uistate.*
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.shareduistate.MediaUiState
import com.berlin.entity.*
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import usecase.*

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
  val movie = mockk<MovieDetails> {
   every { id } returns 1
   every { title } returns "Test Movie"
   every { overview } returns "A test movie"
   every { posterUrl } returns "poster.jpg"
   every { backdropUrl } returns "backdrop.jpg"
   every { releaseDate } returns "2023-01-01"
   every { rating } returns 20.0
   every { runtime } returns 120
   every { genres } returns listOf(GenreEntity(1, "Action"))
   every { productionCompanies } returns listOf(ProductionCompanyEntity(1, "Test Studio"))
   every { hasVideo } returns false
   every { originCountry } returns "US"
   every { duration } returns null
  }
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
  assertThat(state.country).isEqualTo("US")
  assertThat(state.duration).isNull()
  assertThat(viewModel.companyProductionCache).isNotEmpty()
 }

 @Test
 fun `getMediaDetails for tv show should update state with tv show details`() = runTest {
  // Given
  val tvShow = mockk<TvShowDetails> {
   every { id } returns 2
   every { title } returns "Test Show"
   every { overview } returns "A test TV show"
   every { posterUrl } returns "poster.jpg"
   every { backdropUrl } returns "backdrop.jpg"
   every { releaseDate } returns "2022-01-01"
   every { rating } returns 7.5
   every { runtime } returns 45
   every { genres } returns listOf(GenreEntity(2, "Drama"))
   every { productionCompanies } returns listOf(ProductionCompanyEntity(2, "Test Network"))
   every { seasons } returns emptyList()
   every { numberOfSeasons } returns 3
   every { originCountry } returns "US"
  }
  coEvery { getTvShowDetailsUseCase(2, any()) } returns tvShow
  every { savedStateHandle.get<String>("id") } returns "2"
  every { savedStateHandle.get<String>("media_type") } returns MediaType.TV_SHOW.name
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
  viewModel.getMediaDetails(2, MediaType.TV_SHOW, "en")
  advanceUntilIdle()

  // Then
  val state = viewModel.state.value
  assertThat(state.isLoading).isFalse()
  assertThat(state.id).isEqualTo(2)
  assertThat(state.title).isEqualTo("Test Show")
  assertThat(state.overview).isEqualTo("A test TV show")
  assertThat(state.posterUrl).isEqualTo("poster.jpg")
  assertThat(state.backdropUrl).isEqualTo("backdrop.jpg")
  assertThat(state.releaseYear).isEqualTo("2022-01-01")
  assertThat(state.rating).isEqualTo(7.5)
  assertThat(state.runtime).isEqualTo("0h 45m")
  assertThat(state.genres).containsExactly("Drama")
  assertThat(state.numberOfSeasons).isEqualTo(3)
  assertThat(state.country).isEqualTo("US")
  assertThat(viewModel.companyProductionCache).isNotEmpty()
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
  assertThat(state.error).isEqualTo(errorMessage)
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
  val review = mockk<Review> {
   every { id } returns "1"
   every { content } returns "Test review"
   every { date } returns "2023-01-01"
  }
  val reviewUiState = mockk<ReviewUiState> {
   every { id } returns "1"
   every { content } returns "Test review"
   every { date } returns "2023-01-01"
  }
  every { review.toUiState() } returns reviewUiState
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
  assertThat(reviewsContent.items).containsExactly(reviewUiState)
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
  assertThat(noDataState.message).isEqualTo("There is no reviews!")
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
 fun `onShowMediaGalleryClicked with empty gallery should update state with no data found`() = runTest {
  // Given
  coEvery { getMovieGalleryUseCase(1) } returns emptyList()

  // When
  viewModel.onShowMediaGalleryClicked(1, MediaType.MOVIE)
  advanceUntilIdle()

  // Then
  val state = viewModel.state.value
  assertThat(state.rowSection).isInstanceOf(RowSectionUiState.NoDataFound::class.java)
  val noDataState = state.rowSection as RowSectionUiState.NoDataFound
  assertThat(noDataState.message).isEqualTo("There is no gallery!")
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
  val episode = mockk<Episodes> {
   every { id } returns 1
   every { name } returns "Episode 1"
   every { overview } returns "Episode overview"
   every { episodeNumber } returns 1
  }
  val episodeUiState = mockk<EpisodesUiState> {
   every { id } returns 1
   every { name } returns "Episode 1"
   every { overview } returns "Episode overview"
   every { episodeNumber } returns 1
  }
  every { episode.toUiState() } returns episodeUiState
  coEvery { getSeasonEpisodesUseCase(1, 1) } returns listOf(episode)

  // When
  viewModel.onSeasonsClicked(1, 1)
  advanceUntilIdle()

  // Then
  val state = viewModel.state.value
  assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
  val successState = state.rowSection as RowSectionUiState.Success
  assertThat(successState.content).isInstanceOf(TabContent.Season::class.java)
  val seasonContent = successState.content as TabContent.Season
  assertThat(seasonContent.items[1]).isEqualTo(listOf(episodeUiState))
 }

 @Test
 fun `onSeasonsClicked with error should update state with error message`() = runTest {
  // Given
  val errorMessage = "Failed to load episodes"
  coEvery { getSeasonEpisodesUseCase(1, 1) } throws RuntimeException(errorMessage)

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
 fun `toggleMovieDetailsTab to MORE_LIKE_THIS should trigger onShowMoreMediaLikeThisClicked`() = runTest {
  // Given
  val similarMovies = listOf(mockk<Movie> {
   every { toUIStateMedia() } returns mockk()
  })
  coEvery { getSimilarMoviesUseCase(1) } returns similarMovies

  // When
  viewModel.toggleMovieDetailsTab(MovieDetailsTabs.MORE_LIKE_THIS, 1, MediaType.MOVIE)
  advanceUntilIdle()

  // Then
  val state = viewModel.state.value
  assertThat(state.rowSection).isInstanceOf(RowSectionUiState.Success::class.java)
  val successState = state.rowSection as RowSectionUiState.Success
  assertThat(successState.content).isInstanceOf(TabContent.MoreLikeThis::class.java)
  val moreLikeThisContent = successState.content as TabContent.MoreLikeThis
  assertThat(moreLikeThisContent.items).containsExactly(`getMediaDetails for movie should update state with movie details`())
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

 @Test
 fun `onRateIconClicked should send ShowRatingSheet effect`() = runTest {
  // Given
  val effects = mutableListOf<MediaDetailsScreenEffect>()
  val job = launch { viewModel.effect.collect { effects.add(it) } }

  // When
  viewModel.onRateIconClicked(1)
  advanceUntilIdle()

  // Then
  assertThat(effects).containsExactly(MediaDetailsScreenEffect.ShowRatingSheet(id = 1))
  job.cancel()
 }

 @Test
 fun `onAddMediaToFavouriteListClicked should send ShowAddToFavoriteListSheet effect`() = runTest {
  // Given
  val effects = mutableListOf<MediaDetailsScreenEffect>()
  val job = launch { viewModel.effect.collect { effects.add(it) } }

  // When
  viewModel.onAddMediaToFavouriteListClicked(1, 2)
  advanceUntilIdle()

  // Then
  assertThat(effects).containsExactly(MediaDetailsScreenEffect.ShowAddToFavoriteListSheet(favouriteListId = 1, mediaId = 2))
  job.cancel()
 }

 @Test
 fun `onReadMoreDescriptionClicked should toggle expanded state`() = runTest {

  // Given&When
  viewModel.onReadMoreDescriptionClicked()
  advanceUntilIdle()
 }
}