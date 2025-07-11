package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.search_actor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.search_world_tour.WorldTourViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WorldTourViewModel(get()) }
    viewModelOf(::SearchByActorViewModel)
}