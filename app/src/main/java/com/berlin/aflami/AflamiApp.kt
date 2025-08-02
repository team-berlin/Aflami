package com.berlin.aflami

import android.app.Application
import com.berlin.safeimageviewer.FireBaseModelManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AflamiApp: Application() {
    @Inject
    lateinit var modelManager: FireBaseModelManager
    override fun onCreate() {
        super.onCreate()


            CoroutineScope(Dispatchers.IO).launch {
                modelManager.downloadModelsOnce()
            }
        }
    }
