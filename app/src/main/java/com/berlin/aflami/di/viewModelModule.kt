package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.searchworldtour.WorldTourViewModel
import com.berlin.aflami.viewmodel.search_actor.SearchByActorViewModel
import org.koin.core.module.dsl.viewModelOf
import com.berlin.aflami.viewmodel.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::WorldTourViewModel)
    viewModelOf(::SearchByActorViewModel)
    viewModel { WorldTourViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }

}