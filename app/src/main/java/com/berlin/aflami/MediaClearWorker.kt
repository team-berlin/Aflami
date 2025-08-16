package com.berlin.aflami

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import repository.MovieRepository
import repository.TVShowRepository
import usecase.movie.GetTopRatedMoviesUseCase

@HiltWorker
class MediaClearWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,

    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val tvShowRepository: TVShowRepository

) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        movieRepository.getPopularMovies()
        tvShowRepository.getPopularTVShows()

        movieRepository.getTopRatedMovies(1)
        tvShowRepository.getTopRatedTVShows(1)

        //movieRepository.getUpComingMovies()
        return Result.success()
    }
}