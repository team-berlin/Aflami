package com.berlin.aflami.di

import com.berlin.safeimageviewer.FireBaseModelManager
import org.koin.dsl.module

val mlModule = module {
    single { FireBaseModelManager() }
}