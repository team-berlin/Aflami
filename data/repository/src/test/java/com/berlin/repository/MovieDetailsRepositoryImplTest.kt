package com.berlin.repository

import com.berlin.exception.AflamiException
import com.berlin.repository.datasource.local.datasource.GenreLocalDataSource
import com.berlin.repository.datasource.local.datasource.RecentlyWatchedLocalDataSource
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.MoviesGenreEntity
import com.berlin.repository.datasource.remote.RemoteDataSource
import com.berlin.repository.datasource.remote.dto.*
import com.berlin.repository.datasource.remote.dto.details.VideoDto
import com.berlin.repository.datasource.remote.dto.details.VideosResponse
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
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
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class MovieDetailsRepositoryImplTest {

 private val remoteDataSource: RemoteDataSource = mockk()
 private val recentlyWatchedLocalDataSource: RecentlyWatchedLocalDataSource = mockk()
 private val genreLocalDataSource: GenreLocalDataSource = mockk()
 private lateinit var repository: MovieDetailsRepositoryImpl

 @Before
 fun setup() {
  repository = MovieDetailsRepositoryImpl(
   remoteDataSource,
   recentlyWatchedLocalDataSource,
   genreLocalDataSource
  )
 }

 @Test
 fun `should return movie images when remote data source returns valid response`() = runTest {
  // Given
  val movieId = 1L
  val imagesResponse = MediaImagesResponse(
   backdrops = listOf(MediaImageDto(filePath = "/backdrop.jpg", width = 1920, height = 1080)),
   posters = listOf(MediaImageDto(filePath = "/poster.jpg", width = 500, height = 750)),
   id = 1
  )
  coEvery { remoteDataSource.getMovieImages(movieId) } returns imagesResponse

  // When
  val result = repository.getMovieImages(movieId)

  // Then
  assertEquals(listOf("$POSTER_PREFIX/backdrop.jpg"), result.backdrops)
  assertEquals(listOf("$POSTER_PREFIX/poster.jpg"), result.posters)
  coVerify(exactly = 1) { remoteDataSource.getMovieImages(movieId) }
 }

 @Test
 fun `should throw exception when getMovieImages fails`() = runTest {
  // Given
  val movieId = 1L
  coEvery { remoteDataSource.getMovieImages(movieId) } throws AflamiException("Network error")

  // When/Then
  assertThrows<AflamiException> {
   repository.getMovieImages(movieId)
  }
  coVerify(exactly = 1) { remoteDataSource.getMovieImages(movieId) }
 }


 @Test
 fun `should return actors when remote data source returns valid cast`() = runTest {
  // Given
  val movieId = 1L
  val castDto = CastItemDto(id = 1, name = "Actor Name")
  coEvery { remoteDataSource.getMovieCastDetails(movieId) } returns MediaCastResponse(cast = listOf(castDto))

  // When
  val result = repository.getMovieActors(movieId)

  // Then
  assertEquals(1, result.size)
  assertEquals("Actor Name", result[0].name)
  coVerify(exactly = 1) { remoteDataSource.getMovieCastDetails(movieId) }
 }
 @Test
 fun `should return sorted similar movies based on genre preferences`() = runTest {
  // Given
  val movieId = 1L
  val movieDetailsDto = MovieDetailsDto(id = 2, title = "Similar Movie", genres = listOf(GenreDto(id = 1, name = "Action")))
  val reviewDto = ReviewDto(id = "1", content = "Review", author = "Reviewer")
  coEvery { remoteDataSource.getSimilarMovies(movieId) } returns BaseResponse(results = listOf(movieDetailsDto))
  coEvery { remoteDataSource.getMovieReviews(movieId) } returns BaseResponse(results = listOf(reviewDto))
  coEvery { recentlyWatchedLocalDataSource.getCategoryAsPreference() } returns listOf(
   CategoriesPreferencesEntity(
       categoryId = 1,
       count = 5
   ),
   CategoriesPreferencesEntity(
    categoryId = 2,
    count = 6
   )
  )

  // When
  val result = repository.getSimilarMovies(movieId)

  // Then
  assertEquals(1, result.size)
  assertEquals("Similar Movie", result[0].title)
  coVerify(exactly = 1) { remoteDataSource.getSimilarMovies(movieId) }
  coVerify(exactly = 1) { recentlyWatchedLocalDataSource.getCategoryAsPreference() }
 }
 @Test
 fun `should return cached genres when not expired`() = runTest {
  // Given
  val genreEntity = MoviesGenreEntity(id = 1, name = "Action", time = Instant.now().toEpochMilli())
  coEvery { genreLocalDataSource.getCachedMovieGenres() } returns listOf(genreEntity)

  // When
  val result = repository.getMovieGenres()

  // Debug
  println("Result: $result")
  println("Result size: ${result.size}")
  if (result.isNotEmpty()) {
   println("First genre: ${result[0]}")
  }

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].name).isEqualTo("Action")
  coVerify(exactly = 1) { genreLocalDataSource.getCachedMovieGenres() }
  coVerify(exactly = 0) { remoteDataSource.getMovieGenres() }
 }

 @Test
 fun `should fetch and cache genres when cache is expired`() = runTest {
  // Given
  val expiredTime = Instant.now().toEpochMilli() - Constants.CACHE_TIMEOUT - 1000
  val genreEntity = MoviesGenreEntity(id = 1, name = "Action", time = expiredTime)
  val remoteGenre = GenreDto(id = 1, name = "Action")
  coEvery { genreLocalDataSource.getCachedMovieGenres() } returns listOf(genreEntity)
  coEvery { remoteDataSource.getMovieGenres() } returns GenreResponse(genres = listOf(remoteGenre))
  coEvery { genreLocalDataSource.cacheMovieGenres(any()) } just Runs

  // When
  val result = repository.getMovieGenres()

  // Debug
  println("Result: $result")
  println("Result size: ${result.size}")
  if (result.isNotEmpty()) {
   println("First genre: ${result[0]}")
  }

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].name).isEqualTo("Action")
  coVerify(exactly = 1) { genreLocalDataSource.getCachedMovieGenres() }
  coVerify(exactly = 1) { remoteDataSource.getMovieGenres() }
  coVerify(exactly = 1) { genreLocalDataSource.cacheMovieGenres(any()) }
 }

 @Test
 fun `should return movie reviews when remote data source returns valid response`() = runTest {
  // Given
  val movieId = 1L
  val reviewDto = ReviewDto(
   id = "1",
   content = "Great review",
   author = "Reviewer",
   createdAt = "",
   authorDetailsDto = AuthorDetailsDto(name = "Reviewer", avatarPath = "", rating = 5.5)
  )
  coEvery { remoteDataSource.getMovieReviews(movieId) } returns BaseResponse(results = listOf(reviewDto))

  // When
  val result = repository.getMovieReviews(movieId)

  // Debug
  println("Result: $result")
  println("Result size: ${result.size}")
  if (result.isNotEmpty()) {
   println("First review: ${result[0]}")
  }

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].content).isEqualTo("Great review")
  coVerify(exactly = 1) { remoteDataSource.getMovieReviews(movieId) }
 }

 @Test
 fun `should return videos when remote data source returns valid response`() = runTest {
  // Given
  val movieId = 1L
  val videoDto = VideoDto(
   id = "1",
   key = "video_key",
   name = "Trailer",
   site = "YouTube",
   type = "Trailer",
   language = "en"
  )
  coEvery { remoteDataSource.getMovieVideos(movieId) } returns VideosResponse(results = listOf(videoDto))

  // When
  val result = repository.getMovieVideos(movieId)

  // Debug
  println("Result: $result")
  println("Result size: ${result.size}")
  if (result.isNotEmpty()) {
   println("First video: ${result[0]}")
  }

  // Then
  assertThat(result).hasSize(1)
  assertThat(result[0].videoUrl).isEqualTo("https://www.youtube.com/watch?v=video_key")
  coVerify(exactly = 1) { remoteDataSource.getMovieVideos(movieId) }
 }
}
const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w500"
