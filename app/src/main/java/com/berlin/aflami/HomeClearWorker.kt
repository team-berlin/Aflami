package com.berlin.aflami

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil3.ImageLoader
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetPopularMoviesUseCase
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.movie.GetUpComingMoviesUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import usecase.tvshow.GetPopularTVShowsUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.GetTopRatedTVShowUseCase

@HiltWorker
class HomeClearWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,

    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val popularTVShowsUseCase: GetPopularTVShowsUseCase,

    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,

    private val getMoviesGenreUseCase: GetMovieGenresUseCase,
    private val getTVShowGenresUseCase: GetTVShowGenresUseCase,

    private val getWatchedMovieUseCase: ContinueWatchingMovieUseCase,
    private val getWatchedTVShowUseCase: ContinueWatchingTVShowUseCase,

    private val getTopRatedSeriesUseCase: GetTopRatedTVShowUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,

    ) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {

            getMoviesGenreUseCase()
            getTVShowGenresUseCase()
            popularMoviesUseCase()
            popularTVShowsUseCase()
            getUpComingMoviesUseCase(-1)
            getWatchedMovieUseCase(1)
            getWatchedTVShowUseCase(1)
            getTopRatedSeriesUseCase(1)
            getTopRatedMoviesUseCase(1)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}


