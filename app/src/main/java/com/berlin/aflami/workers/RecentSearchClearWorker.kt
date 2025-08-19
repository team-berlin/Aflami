package com.berlin.aflami.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import usecase.movie.ClearMoviesSearchHistoryUseCase
import usecase.tvshow.ClearTVShowSearchHistoryUseCase

@HiltWorker
class RecentSearchClearWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,

    private val clearMoviesSearchHistoryUseCase: ClearMoviesSearchHistoryUseCase,
    private val clearTvShowSearchHistoryUseCase: ClearTVShowSearchHistoryUseCase,

    ) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            clearMoviesSearchHistoryUseCase()
            clearTvShowSearchHistoryUseCase()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}


