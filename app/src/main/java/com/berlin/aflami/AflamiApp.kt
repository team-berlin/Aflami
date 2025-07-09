package com.berlin.aflami

import android.app.Application
import com.berlin.aflami.di.appModule
import com.berlin.aflami.di.dataSourceModule
import com.berlin.aflami.di.repositoryModule
import com.berlin.aflami.di.useCaseModule
import com.berlin.aflami.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AflamiApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AflamiApp)
            modules(
                appModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}