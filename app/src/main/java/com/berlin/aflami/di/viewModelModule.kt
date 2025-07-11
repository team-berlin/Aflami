package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.searchworldtour.WorldTourViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WorldTourViewModel(get()) }
}