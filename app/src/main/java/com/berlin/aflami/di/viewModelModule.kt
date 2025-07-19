package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.searchworldtour.WorldTourViewModel
import com.berlin.aflami.viewmodel.search_actor.SearchByActorViewModel
import org.koin.core.module.dsl.viewModelOf
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::SearchByActorViewModel)
    viewModel { SearchByCountryViewModel(get()) }
    viewModelOf(::SearchViewModel)
    viewModelOf(::SearchByCountryViewModel)
    viewModel { WorldTourViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModelOf(:: MediaDetailsViewmodel)
}