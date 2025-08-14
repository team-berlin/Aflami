package com.berlin.aflami

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.berlin.repository.MediaClearWorker
import com.berlin.safeimageviewer.FireBaseModelManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class AflamiApp
    : Application(),
    Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var modelManager: FireBaseModelManager
    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, workManagerConfiguration) // Ensure HiltWorkerFactory is set
        Log.d("WOWTEST", "onCreate: WorkManager initialized with factory: ${workManagerConfiguration.workerFactory}")
        CoroutineScope(Dispatchers.IO).launch {
            modelManager.downloadModelsOnce()
        }
        Log.d("WOWTEST", "onCreate: workerFactory = $workerFactory")
        scheduleMovieRefresh(this)

    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    fun scheduleMovieRefresh(context: Context) {
        val request = OneTimeWorkRequestBuilder<MediaClearWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "movie_refresh_worker",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}


