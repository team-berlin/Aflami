import com.berlin.repository.TvShowDetailsRepositoryImpl
import com.berlin.repository.datasource.remote.dto.details.EpisodeDto
import com.berlin.exception.AflamiException
import com.berlin.repository.POSTER_PREFIX
import com.berlin.repository.datasource.local.datasource.GenreLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.*
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.details.VideoDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import com.berlin.repository.util.Constants
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class TvShowDetailsRepositoryImplTest {

 private val remoteDataSource: RemoteDataSource = mockk()
 private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource = mockk()
 private val genreLocalDataSource: GenreLocalDataSource = mockk()
 private lateinit var repository: TvShowDetailsRepositoryImpl

 @Before
 fun setup() {
  repository = TvShowDetailsRepositoryImpl(
   remoteDataSource,
   recentlyWatchedLocalDataSource,
   genreLocalDataSource
  )
 }

 @Test
 fun `should return TV show details with images and video availability when remote data source returns valid response`() = runTest {
  // Given
  val seriesId = 1L
  val tvShowDetailsDto = TVShowDetailsDto(
   id = seriesId.toInt(),
   name = "Test TV Show",
   firstAirDate = "2023-10-01",
   posterPath = "/poster.jpg",
   overview = "A test TV show description",
   genres = listOf(GenreDto(id = 1, name = "Drama")),
   numberOfSeasons = 2,
   voteAverage = 8.0,
   backdropPath = "/backdrop.jpg"
  )
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(MediaImageDto(filePath = "/backdrop.jpg", width = 1920, height = 1080)),
   posters = listOf(MediaImageDto(filePath = "/poster.jpg", width = 500, height = 750)),
   id = 1
  )
  val videoDto = VideoDto(id = "1", key = "video_key", name = "Trailer", site = "YouTube", type = "Trailer", language = "en")
  coEvery { remoteDataSource.getTVShowDetailsById(seriesId) } returns tvShowDetailsDto
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = listOf(videoDto))

  // When
  val result = repository.getTVShowDetails(seriesId)

  // Then
  assertThat(result.id).isEqualTo(seriesId.toInt())
  assertThat(result.title).isEqualTo("Test TV Show")
  assertThat(result.genres).hasSize(1)
  assertThat(result.genres[0].name).isEqualTo("Drama")
  assertThat(result.galleryUrl).containsExactly("$POSTER_PREFIX/backdrop.jpg")
  assertThat(result.hasVideo).isTrue()
  coVerify(exactly = 1) { remoteDataSource.getTVShowDetailsById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }

 @Test
 fun `should return empty images and no video when getTVShowsImages and getTVShowVideos fail`() = runTest {
  // Given
  val seriesId = 1L
  val tvShowDetailsDto = TVShowDetailsDto(
   id = seriesId.toInt(),
   name = "Test TV Show",
   firstAirDate = "2023-10-01",
   posterPath = "/poster.jpg",
   overview = "A test TV show description",
   genres = listOf(GenreDto(id = 1, name = "Drama")),
   numberOfSeasons = 2,
   voteAverage = 8.0,
   backdropPath = "/backdrop.jpg"
  )
  coEvery { remoteDataSource.getTVShowDetailsById(seriesId) } returns tvShowDetailsDto
  coEvery { remoteDataSource.getTVImagesById(seriesId) } throws AflamiException("Network error")
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } throws AflamiException("Network error")

  // When
  val result = repository.getTVShowDetails(seriesId)

  // Then
  assertThat(result.id).isEqualTo(seriesId.toInt())
  assertThat(result.galleryUrl).isEmpty()
  assertThat(result.hasVideo).isFalse()
  coVerify(exactly = 1) { remoteDataSource.getTVShowDetailsById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }

 @Test
 fun `should return TV show images when remote data source returns valid response`() = runTest {
  // Given
  val seriesId = 1L
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(MediaImageDto(filePath = "/backdrop.jpg", width = 1920, height = 1080)),
   posters = listOf(MediaImageDto(filePath = "/poster.jpg", width = 500, height = 750)),
   id = 1
  )
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse

  // When
  val result = repository.getTVShowsImages(seriesId)

  // Then
  assertThat(result.backdrops).containsExactly("$POSTER_PREFIX/backdrop.jpg")
  assertThat(result.posters).containsExactly("$POSTER_PREFIX/poster.jpg")
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
 }

 @Test
 fun `should throw exception when getTVShowsImages fails`() = runTest {
  // Given
  val seriesId = 1L
  coEvery { remoteDataSource.getTVImagesById(seriesId) } throws AflamiException("Network error")

  // When/Then
  assertThrows<AflamiException> {
   repository.getTVShowsImages(seriesId)
  }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
 }

 @Test
 fun `should return actors when remote data source returns valid cast`() = runTest {
  // Given
  val seriesId = 1L
  val castDto = CastItemDto(id = 1, name = "Actor Name")
  coEvery { remoteDataSource.getTVCastDetailsById(seriesId) } returns MediaCastResponse(cast = listOf(castDto))

  // When
  val result = repository.getTVShowsCastDetails(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].name).isEqualTo("Actor Name")
  coVerify(exactly = 1) { remoteDataSource.getTVCastDetailsById(seriesId) }
 }

 @Test
 fun `should return sorted similar TV shows based on genre preferences`() = runTest {
  // Given
  val seriesId = 1L
  val tvShowDetailsDto = TVShowDetailsDto(
   id = 2,
   name = "Similar TV Show",
   genres = listOf(GenreDto(id = 1, name = "Drama"))
  )
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(MediaImageDto(filePath = "/backdrop.jpg", width = 1920, height = 1080)),
   posters = listOf(MediaImageDto(filePath = "/poster.jpg", width = 500, height = 750)),
   id = 2
  )
  val videoDto = VideoDto(id = "1", key = "video_key", name = "Trailer", site = "YouTube", type = "Trailer", language = "en")
  coEvery { remoteDataSource.getSimilarTVById(seriesId) } returns BaseResponse(results = listOf(tvShowDetailsDto))
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = listOf(videoDto))
  coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns listOf(
   CategoriesPreferencesEntity(categoryId = 1, count = 5),
   CategoriesPreferencesEntity(categoryId = 2, count = 3)
  )

  // When
  val result = repository.getTVShowsSimilar(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].title).isEqualTo("Similar TV Show")
  assertThat(result[0].galleryUrl).containsExactly("$POSTER_PREFIX/backdrop.jpg")
  assertThat(result[0].hasVideo).isTrue()
  coVerify(exactly = 1) { remoteDataSource.getSimilarTVById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
  coVerify(exactly = 1) { recentlyWatchedLocalDataSource.getCategoryAsPreference() }
 }

 @Test
 fun `should return TV show reviews when remote data source returns valid response`() = runTest {
  // Given
  val seriesId = 1L
  val reviewDto = ReviewDto(
   id = "1",
   content = "Great review",
   author = "Reviewer",
   createdAt = "",
   authorDetailsDto = AuthorDetailsDto(name = "Reviewer", avatarPath = "", rating = 5.5)
  )
  coEvery { remoteDataSource.getTVShowReviewsById(seriesId) } returns BaseResponse(results = listOf(reviewDto))

  // When
  val result = repository.getTVShowReviews(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].content).isEqualTo("Great review")
  coVerify(exactly = 1) { remoteDataSource.getTVShowReviewsById(seriesId) }
 }

 @Test
 fun `should return season episodes when remote data source returns valid response`() = runTest {
  // Given
  val seriesId = 1L
  val seasonNumber = 1
  val episodeDto = EpisodeDto(id = 1, name = "Episode 1", episodeNumber = 1, seasonNumber = 1)
  coEvery { remoteDataSource.getEpisodeSeasonTV(seriesId, seasonNumber) } returns SeasonEpisodesDto(episodes = listOf(episodeDto))

  // When
  val result = repository.getSeasonEpisodes(seriesId, seasonNumber)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].name).isEqualTo("Episode 1")
  coVerify(exactly = 1) { remoteDataSource.getEpisodeSeasonTV(seriesId, seasonNumber) }
 }

 @Test
 fun `should return cached genres when not expired`() = runTest {
  // Given
  val genreEntity = TVShowGenreEntity(id = 1, name = "Drama", time = Instant.now().toEpochMilli())
  coEvery { genreLocalDataSource.getCachedTVGenres() } returns listOf(genreEntity)

  // When
  val result = repository.getTVShowsGenres()

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].name).isEqualTo("Drama")
  coVerify(exactly = 1) { genreLocalDataSource.getCachedTVGenres() }
  coVerify(exactly = 0) { remoteDataSource.getTVGenres() }
 }

 @Test
 fun `should fetch and cache genres when cache is expired`() = runTest {
  // Given
  val expiredTime = Instant.now().toEpochMilli() - Constants.CACHE_TIMEOUT - 1000
  val genreEntity = TVShowGenreEntity(id = 1, name = "Drama", time = expiredTime)
  val remoteGenre = GenreDto(id = 1, name = "Drama")
  coEvery { genreLocalDataSource.getCachedTVGenres() } returns listOf(genreEntity)
  coEvery { remoteDataSource.getTVGenres() } returns GenreResponse(genres = listOf(remoteGenre))
  coEvery { genreLocalDataSource.cacheTVGenres(any()) } just Runs

  // When
  val result = repository.getTVShowsGenres()

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].name).isEqualTo("Drama")
  coVerify(exactly = 1) { genreLocalDataSource.getCachedTVGenres() }
  coVerify(exactly = 1) { remoteDataSource.getTVGenres() }
  coVerify(exactly = 1) { genreLocalDataSource.cacheTVGenres(any()) }
 }

 @Test
 fun `should return videos when remote data source returns valid response`() = runTest {
  // Given
  val seriesId = 1L
  val videoDto = VideoDto(
   id = "1",
   key = "video_key",
   name = "Trailer",
   site = "YouTube",
   type = "Trailer",
   language = "en"
  )
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = listOf(videoDto))

  // When
  val result = repository.getTVShowVideos(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].videoUrl).isEqualTo("https://www.youtube.com/watch?v=video_key")
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }
}
