package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.mediadetails.MediaDetailsViewmodel
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.berlin.aflami.viewmodel.login.LoginViewmodel
import com.berlin.aflami.viewmodel.main.MainViewModel

val viewModelModule = module {

    viewModelOf(::LoginViewmodel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::SearchByActorViewModel)
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::MediaDetailsViewmodel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::MediaDetailsViewmodel)
}