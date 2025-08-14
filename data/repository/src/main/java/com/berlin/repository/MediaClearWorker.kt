package com.berlin.repository

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import repository.MovieRepository

@HiltWorker
class MediaClearWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,

    private val movieRepository: MovieRepository

) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("WOWTEST", "doWork: repo = $movieRepository")
        movieRepository.getPopularMovies()
        movieRepository.getTopRatedMovies(1)
        movieRepository.getUpComingMovies()
        Log.d("WOWTEST", "doWork: ")
        return Result.success()
    }
}