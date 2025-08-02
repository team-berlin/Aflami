package com.berlin.aflami.di

import com.berlin.aflami.viewmodel.home.HomeViewModel
import com.berlin.aflami.viewmodel.home.toprating.TopRatingViewModel
import com.berlin.aflami.viewmodel.login.LoginViewmodel
import com.berlin.aflami.viewmodel.main.MainViewModel
import com.berlin.aflami.viewmodel.mediadetails.cast.CastViewModel
import com.berlin.aflami.viewmodel.mediadetails.details.MediaDetailsViewModel
import com.berlin.aflami.viewmodel.search.SearchViewModel
import com.berlin.aflami.viewmodel.searchactor.SearchByActorViewModel
import com.berlin.aflami.viewmodel.searchcountry.SearchByCountryViewModel
import com.berlin.aflami.viewmodel.shareduistate.MediaType
import com.berlin.aflami.viewmodel.home.continueWatching.ContinueWatchingMediaViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::LoginViewmodel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SearchByActorViewModel)
    viewModelOf(::SearchViewModel)
//    viewModelOf(::MediaDetailsViewModel)
    viewModel { (mediaId: Long, mediaType: MediaType, savedStateHandle: androidx.lifecycle.SavedStateHandle) ->
        MediaDetailsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            mediaId,
            mediaType
        )
    }
    viewModelOf(::SearchByCountryViewModel)
    viewModelOf(::CastViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ContinueWatchingMediaViewModel)
    viewModelOf(::TopRatingViewModel)
}