package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.login.LoginViewmodel
import com.berlin.aflami.viewmodel.main.MainViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.mediadetails.cast.CastViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.search.SearchViewModel

import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryViewModel
import com.berlin.aflami.viewmodel.watchedmedia.ContinueWatchingMediaViewModel
import com.berlin.aflami.viewmodel.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::LoginViewmodel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SearchByActorViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::MediaDetailsViewModel)
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::CastViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ContinueWatchingMediaViewModel)
}