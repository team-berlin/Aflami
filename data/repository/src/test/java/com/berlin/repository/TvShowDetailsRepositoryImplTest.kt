package com.berlin.repository

import com.berlin.entity.*
import com.berlin.repository.datasource.local.GenreLocalDataSource
import com.berlin.repository.datasource.local.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.TVShowGenreEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.*
import com.berlin.repository.datasource.remote.dto.details.EpisodeDto
import com.berlin.repository.datasource.remote.dto.details.SeasonEpisodesDto
import com.berlin.repository.datasource.remote.dto.details.VideoDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.datasource.remote.response.GenreResponse
import com.berlin.repository.datasource.remote.response.MediaCastResponse
import com.berlin.repository.datasource.remote.response.MediaImagesResponse
import com.berlin.repository.util.Constants
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TvShowDetailsRepositoryImplTest {

 private val remoteDataSource: RemoteDataSource = mockk()
 private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource = mockk()
 private val genreLocalDataSource: GenreLocalDataSource = mockk()
 private lateinit var repository: TvShowDetailsRepositoryImpl

 @BeforeEach
 fun setup() {
  repository = TvShowDetailsRepositoryImpl(
   remoteDataSource,
   recentlyWatchedLocalDataSource,
   genreLocalDataSource
  )
 }

 // Test cases for getTVShowDetails
 @Test
 fun `should return TV show details with gallery images, video availability, and reviews when remote data source returns valid response`() = runTest {
  // Given
  val seriesId = 1L
  val tvShowDetailsDto = TVShowDetailsDto(
   id = 1,
   name = "Test TV Show",
   firstAirDate = "2023-10-01",
   posterPath = "/poster.jpg",
   overview = "A test TV show description",
   genres = listOf(GenreDto(id = 1, name = "Drama")),
   episodeRunTime = listOf(45),
   numberOfSeasons = 2,
   productionCompanies = listOf(ProductionCompanyDto(id = 1, name = "Test Studio", logoPath = "/logo.jpg", originCountry = "US")),
   originCountry = listOf("US"),
   voteAverage = 8.5,
   backdropPath = "/backdrop.jpg"
  )
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(
    MediaImageDto(filePath = "/backdrop1.jpg", width = 1920, height = 1080),
    MediaImageDto(filePath = "/backdrop2.jpg", width = 1920, height = 1080)
   ),
   posters = listOf(MediaImageDto(filePath = "/poster.jpg", width = 500, height = 750)),
   id = 1
  )
  val videoDto = VideoDto(
   id = "1",
   key = "video_key",
   name = "Trailer",
   site = "YouTube",
   type = "Trailer",
   language = "en"
  )
  val reviewDto = ReviewDto(
   id = "1",
   content = "Amazing show!",
   author = "Reviewer",
   createdAt = "2023-10-01",
   authorDetailsDto = AuthorDetailsDto(name = "Reviewer", avatarPath = "/avatar.jpg", rating = 8.0)
  )
  coEvery { remoteDataSource.getTVShowDetailsById(seriesId) } returns tvShowDetailsDto
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = listOf(videoDto))
  coEvery { remoteDataSource.getTVShowReviewsById(seriesId) } returns BaseResponse(results = listOf(reviewDto))

  // When
  val result = repository.getTVShowDetails(seriesId)

  // Then
  assertThat(result).isInstanceOf(TVShow::class.java)
  assertThat(result.id).isEqualTo(seriesId.toInt())
  assertThat(result.title).isEqualTo("Test TV Show")
  assertThat(result.rating).isEqualTo(8.5)
  assertThat(result.genres).hasSize(1)
  assertThat(result.genres[0].name).isEqualTo("Drama")
  assertThat(result.galleryUrl).containsExactly(
   "$POSTER_PREFIX/backdrop1.jpg",
   "$POSTER_PREFIX/backdrop2.jpg"
  )
  assertThat(result.hasVideo).isTrue()
  coVerify(exactly = 1) { remoteDataSource.getTVShowDetailsById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowReviewsById(seriesId) }
 }

 @Test
 fun `should return TV show details with empty gallery, no videos, and empty reviews when image, video, and review fetches fail`() = runTest {
  // Given
  val seriesId = 1L
  val tvShowDetailsDto = TVShowDetailsDto(
   id = 1,
   name = "Test TV Show",
   firstAirDate = "2023-10-01",
   posterPath = "/poster.jpg",
   overview = "A test TV show description",
   genres = listOf(GenreDto(id = 1, name = "Drama")),
   episodeRunTime = listOf(45),
   numberOfSeasons = 2,
   productionCompanies = listOf(ProductionCompanyDto(id = 1, name = "Test Studio", logoPath = "/logo.jpg", originCountry = "US")),
   originCountry = listOf("US"),
   voteAverage = 8.5,
   backdropPath = "/backdrop.jpg"
  )
  coEvery { remoteDataSource.getTVShowDetailsById(seriesId) } returns tvShowDetailsDto
  coEvery { remoteDataSource.getTVImagesById(seriesId) } throws Exception("Image fetch failed")
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } throws Exception("Video fetch failed")
  coEvery { remoteDataSource.getTVShowReviewsById(seriesId) } returns BaseResponse(results = null)

  // When
  val result = repository.getTVShowDetails(seriesId)

  // Then
  assertThat(result).isInstanceOf(TVShow::class.java)
  assertThat(result.id).isEqualTo(seriesId.toInt())
  assertThat(result.title).isEqualTo("Test TV Show")
  assertThat(result.galleryUrl).isEmpty()
  assertThat(result.hasVideo).isFalse()
  coVerify(exactly = 1) { remoteDataSource.getTVShowDetailsById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowReviewsById(seriesId) }
 }

 // Test cases for getTVShowsImages
 @Test
 fun `should return MediaImage with backdrops and posters when remote data source returns valid images`() = runTest {
  // Given
  val seriesId = 1L
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(
    MediaImageDto(filePath = "/backdrop1.jpg", width = 1920, height = 1080),
    MediaImageDto(filePath = "/backdrop2.jpg", width = 1920, height = 1080)
   ),
   posters = listOf(MediaImageDto(filePath = "/poster.jpg", width = 500, height = 750)),
   id = 1
  )
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse

  // When
  val result = repository.getTVShowsImages(seriesId)

  // Then
  assertThat(result).isInstanceOf(MediaImage::class.java)
  assertThat(result.backdrops).containsExactly(
   "$POSTER_PREFIX/backdrop1.jpg",
   "$POSTER_PREFIX/backdrop2.jpg"
  )
  assertThat(result.posters).containsExactly("$POSTER_PREFIX/poster.jpg")
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
 }

 @Test
 fun `should throw exception when getTVShowsImages fails`() = runTest {
  // Given
  val seriesId = 1L
  coEvery { remoteDataSource.getTVImagesById(seriesId) } throws Exception("Image fetch failed")

  // When
  val exception = runCatching { repository.getTVShowsImages(seriesId) }.exceptionOrNull()

  // Then
  assertThat(exception).isNotNull()
  assertThat(exception?.message).isEqualTo("Image fetch failed")
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
 }

 // Test cases for getTVShowsCastDetails
 @Test
 fun `should return list of actors when remote data source returns valid cast details`() = runTest {
  // Given
  val seriesId = 1L
  val castDto = CastItemDto(
   id = 1,
   name = "Actor Name",
   character = "Character Name",
   profilePath = "/profile.jpg"
  )
  val castResponse = MediaCastResponse(cast = listOf(castDto))
  coEvery { remoteDataSource.getTVCastDetailsById(seriesId) } returns castResponse

  // When
  val result = repository.getTVShowsCastDetails(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0]).isInstanceOf(Actor::class.java)
  assertThat(result[0].name).isEqualTo("Actor Name")
  coVerify(exactly = 1) { remoteDataSource.getTVCastDetailsById(seriesId) }
 }

 @Test
 fun `should return empty list when cast details are null`() = runTest {
  // Given
  val seriesId = 1L
  coEvery { remoteDataSource.getTVCastDetailsById(seriesId) } returns MediaCastResponse(cast = null)

  // When
  val result = repository.getTVShowsCastDetails(seriesId)

  // Then
  assertThat(result).isEmpty()
  coVerify(exactly = 1) { remoteDataSource.getTVCastDetailsById(seriesId) }
 }

 // Test cases for getTVShowsSimilar
 @Test
 fun `should return sorted list of similar TV shows based on genre preferences`() = runTest {
  // Given
  val seriesId = 1L
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(MediaImageDto(filePath = "/backdrop1.jpg", width = 1920, height = 1080)),
   posters = emptyList(),
   id = 1
  )
  val videoDto = VideoDto(id = "1", key = "video_key", name = "Trailer", site = "YouTube", type = "Trailer", language = "en")
  val tvShowDto = TVShowDetailsDto(
   id = 2,
   name = "Similar Show",
   genres = listOf(GenreDto(id = 1, name = "Drama")),
   voteAverage = 7.5,
   firstAirDate = "2023-01-01",
   posterPath = "/poster.jpg",
   overview = "Similar show description",
   episodeRunTime = listOf(60),
   numberOfSeasons = 1,
   productionCompanies = emptyList(),
   originCountry = emptyList(),
   backdropPath = "/backdrop.jpg"
  )
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = listOf(videoDto))
  coEvery { remoteDataSource.getSimilarTVById(seriesId) } returns BaseResponse(results = listOf(tvShowDto))
  coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns listOf(
   CategoriesPreferencesEntity(categoryId = 1, count = 5)
  )

  // When
  val result = repository.getTVShowsSimilar(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].title).isEqualTo("Similar Show")
  assertThat(result[0].galleryUrl).containsExactly("$POSTER_PREFIX/backdrop1.jpg")
  assertThat(result[0].hasVideo).isTrue()
  coVerify(exactly = 1) { remoteDataSource.getSimilarTVById(seriesId) }
  coVerify(exactly = 1) { recentlyWatchedLocalDataSource.getCategoryAsPreference() }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }

 @Test
 fun `should return empty list when similar TV shows are null`() = runTest {
  // Given
  val seriesId = 1L
  val imagesResponse = MediaImagesResponse(backdrops = emptyList(), posters = emptyList(), id = 1)
  coEvery { remoteDataSource.getTVImagesById(seriesId) } returns imagesResponse
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = emptyList())
  coEvery { remoteDataSource.getSimilarTVById(seriesId) } returns BaseResponse(results = null)
  coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns emptyList()

  // When
  val result = repository.getTVShowsSimilar(seriesId)

  // Then
  assertThat(result).isEmpty()
  coVerify(exactly = 1) { remoteDataSource.getSimilarTVById(seriesId) }
  coVerify(exactly = 1) { recentlyWatchedLocalDataSource.getCategoryAsPreference() }
  coVerify(exactly = 1) { remoteDataSource.getTVImagesById(seriesId) }
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }

 // Test cases for getTVShowReviews
 @Test
 fun `should return list of reviews when remote data source returns valid reviews`() = runTest {
  // Given
  val seriesId = 1L
  val reviewDto = ReviewDto(
   id = "1",
   content = "Amazing show!",
   author = "Reviewer",
   createdAt = "2023-10-01",
   authorDetailsDto = AuthorDetailsDto(name = "Reviewer", avatarPath = "/avatar.jpg", rating = 8.0)
  )
  coEvery { remoteDataSource.getTVShowReviewsById(seriesId) } returns BaseResponse(results = listOf(reviewDto))

  // When
  val result = repository.getTVShowReviews(seriesId)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0]).isInstanceOf(Review::class.java)
  assertThat(result[0].content).isEqualTo("Amazing show!")
  coVerify(exactly = 1) { remoteDataSource.getTVShowReviewsById(seriesId) }
 }

 @Test
 fun `should return empty list when reviews are null or empty`() = runTest {
  // Given
  val seriesId = 1L
  coEvery { remoteDataSource.getTVShowReviewsById(seriesId) } returns BaseResponse(results = null)

  // When
  val result = repository.getTVShowReviews(seriesId)

  // Then
  assertThat(result).isEmpty()
  coVerify(exactly = 1) { remoteDataSource.getTVShowReviewsById(seriesId) }
 }

 // Test cases for getSeasonEpisodes
 @Test
 fun `should return list of episodes when remote data source returns valid episodes`() = runTest {
  // Given
  val seriesId = 1L
  val seasonNumber = 1
  val episodeDto = EpisodeDto(
   id = 1,
   name = "Episode 1",
   episodeNumber = 1,
   seasonNumber = 1,
   airDate = "2023-10-01",
   overview = "Episode description",
   stillPath = "/still.jpg"
  )
  val seasonResponse = SeasonEpisodesDto(episodes = listOf(episodeDto))
  coEvery { remoteDataSource.getEpisodeSeasonTV(seriesId, seasonNumber) } returns seasonResponse

  // When
  val result = repository.getSeasonEpisodes(seriesId, seasonNumber)

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0]).isInstanceOf(Episode::class.java)
  assertThat(result[0].name).isEqualTo("Episode 1")
  coVerify(exactly = 1) { remoteDataSource.getEpisodeSeasonTV(seriesId, seasonNumber) }
 }

 @Test
 fun `should return empty list when episodes are null`() = runTest {
  // Given
  val seriesId = 1L
  val seasonNumber = 1
  coEvery { remoteDataSource.getEpisodeSeasonTV(seriesId, seasonNumber) } returns SeasonEpisodesDto(episodes = null)

  // When
  val result = repository.getSeasonEpisodes(seriesId, seasonNumber)

  // Then
  assertThat(result).isEmpty()
  coVerify(exactly = 1) { remoteDataSource.getEpisodeSeasonTV(seriesId, seasonNumber) }
 }

 // Test cases for getTVShowsGenres
 @Test
 fun `should return cached genres when cache is not expired or empty`() = runTest {
  // Given
  val genreEntity = TVShowGenreEntity(id = 1, name = "Drama", time = System.currentTimeMillis())
  coEvery { genreLocalDataSource.getCachedTVGenres() } returns listOf(genreEntity)

  // When
  val result = repository.getTVShowsGenres()

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0]).isInstanceOf(Genre::class.java)
  assertThat(result[0].name).isEqualTo("Drama")
  coVerify(exactly = 1) { genreLocalDataSource.getCachedTVGenres() }
  coVerify(exactly = 0) { remoteDataSource.getTVGenres() }
 }

 @Test
 fun `should fetch and cache remote genres when cache is empty`() = runTest {
  // Given
  val genreDto = GenreDto(id = 1, name = "Drama")
  coEvery { genreLocalDataSource.getCachedTVGenres() } returns emptyList()
  coEvery { remoteDataSource.getTVGenres() } returns GenreResponse(genres = listOf(genreDto))
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
 fun `should fetch and cache remote genres when cache is expired`() = runTest {
  // Given
  val expiredTime = System.currentTimeMillis() - Constants.CACHE_TIMEOUT - 1000
  val genreEntity = TVShowGenreEntity(id = 1, name = "Drama", time = expiredTime)
  val genreDto = GenreDto(id = 1, name = "Drama")
  coEvery { genreLocalDataSource.getCachedTVGenres() } returns listOf(genreEntity)
  coEvery { remoteDataSource.getTVGenres() } returns GenreResponse(genres = listOf(genreDto))
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

 // Test cases for getTVShowVideos
 @Test
 fun `should return list of videos when remote data source returns valid videos`() = runTest {
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
  assertThat(result[0]).isInstanceOf(Video::class.java)
  assertThat(result[0]).isEqualTo("Trailer")
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }

 @Test
 fun `should return empty list when videos are null`() = runTest {
  // Given
  val seriesId = 1L
  coEvery { remoteDataSource.getTVShowVideos(seriesId) } returns VideosResponse(results = null)

  // When
  val result = repository.getTVShowVideos(seriesId)

  // Then
  assertThat(result).isEmpty()
  coVerify(exactly = 1) { remoteDataSource.getTVShowVideos(seriesId) }
 }
}

