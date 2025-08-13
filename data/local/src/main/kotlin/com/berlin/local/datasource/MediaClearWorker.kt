package com.berlin.local.datasource

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.berlin.local.dao.HomeMovieDao
import com.berlin.local.dao.HomeTVShowDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MediaClearWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val homeMovieDao : HomeMovieDao,
    private val tvShowDao: HomeTVShowDao

) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        homeMovieDao.clearAllMovies()
        tvShowDao.clearAllTVShows()
        return Result.success()
    }
}