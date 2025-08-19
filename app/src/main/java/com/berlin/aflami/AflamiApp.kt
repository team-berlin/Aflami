package com.berlin.aflami

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.berlin.aflami.workers.HomeClearWorker
import com.berlin.aflami.workers.RecentSearchClearWorker
import com.berlin.safeimageviewer.FireBaseModelManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class AflamiApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var modelManager: FireBaseModelManager
    override fun onCreate() {
        super.onCreate()
        scheduleNextSync(this)
        clearSearchHistory(this)
        CoroutineScope(Dispatchers.IO).launch {
            modelManager.downloadModelsOnce()
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    private fun scheduleNextSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val mediaClearWork = PeriodicWorkRequestBuilder<HomeClearWorker>(
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "MediaClearWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                mediaClearWork
            )
    }

    private fun clearSearchHistory(context: Context) {
        val recentSearchClearWorker = PeriodicWorkRequestBuilder<RecentSearchClearWorker>(
            1, TimeUnit.HOURS
        )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "RecentSearchClearWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                recentSearchClearWorker
            )
    }
}


