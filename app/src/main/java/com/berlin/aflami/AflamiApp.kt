package com.berlin.aflami

import android.app.Application
import android.util.Log
import com.berlin.aflami.di.appModule
import com.berlin.aflami.di.daoModule
import com.berlin.aflami.di.dataSourceModule
import com.berlin.aflami.di.mlModule
import com.berlin.aflami.di.networkModule
import com.berlin.aflami.di.repositoryModule
import com.berlin.aflami.di.useCaseModule
import com.berlin.aflami.di.viewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.berlin.safeimageviewer.FireBaseModelManager
import org.koin.android.ext.android.get

class AflamiApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AflamiApp)
            modules(
                mlModule,
                appModule,
                networkModule,
                dataSourceModule,
                daoModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )

        }
        Log.d("WOW", "onCreate:")
        CoroutineScope(Dispatchers.Default).launch {
            get<FireBaseModelManager>().downloadModelsOnce()
            Log.d("WOW", "Models downloadeding successfully")
        }

    }
}