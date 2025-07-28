package com.berlin.repository

import com.berlin.repository.datasource.remote.HomeRemoteDataSource
import com.berlin.repository.fake.FakeHomeRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import repository.MovieRepository

class HomeRepositoryImplTest {
    private lateinit var homeRemoteDataSource: HomeRemoteDataSource
    private lateinit var homeRepository: MovieRepository

    @Before
    fun setUp(){
        homeRemoteDataSource = mockk(relaxed = true)
        homeRepository = HomeRepositoryImpl(homeRemoteDataSource)
    }

    @Test(expected = Exception::class)
    fun `should throw exception if getTopRatedSeries in remote data source thrown exception`() = runTest{
        val page = 1
        coEvery {homeRemoteDataSource.getTopRatedSeries(page) } throws Exception("error")
        homeRepository.getTopRatedSeries(page)
    }
    @Test(expected = Exception::class)
    fun `should throw exception if getTopRatedMovies in remote data source thrown exception`() = runTest{
        val page = 1
        coEvery {homeRemoteDataSource.getTopRatedMovies(page) } throws Exception("error")
        homeRepository.getTopRatedMovies(page)
    }

    @Test
    fun `should call getTopRatedSeries in remote data source once `() = runTest{
        val page = 1
        // When
        homeRepository.getTopRatedSeries(page)
        // Then
        coVerify(exactly = 1) { homeRemoteDataSource.getTopRatedSeries(page) }
    }

    @Test
    fun `should call getTopRatedMovies in remote data source once` ()= runTest {
        val page = 1
        // when
        homeRepository.getTopRatedMovies(page)
        // Then
        coVerify(exactly = 1) { homeRemoteDataSource.getTopRatedMovies(page) }
    }
//
//    @Test
//    fun `should map TopRatedMoviesResponse to return list of Movies`() = runTest{
//        val page = 1
////        coEvery { homeRemoteDataSource.getTopRatedMovies(1) } returns
//        val result = homeRepository.getTopRatedMovies(1)
//        assert(result.size == 1)
//    }

}