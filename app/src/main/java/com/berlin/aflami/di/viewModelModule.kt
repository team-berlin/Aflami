package com.berlin.aflami.di

//import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import org.koin.core.module.dsl.viewModelOf
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::SearchByActorViewModel)
    viewModel { SearchByCountryViewModel(get()) }
    viewModelOf(::SearchViewModel)
    viewModelOf(::SearchByCountryViewModel)
    viewModel { SearchViewModel(get(), get(), get(), get(),get(),get()) }
    viewModelOf(:: MediaDetailsViewmodel)
}