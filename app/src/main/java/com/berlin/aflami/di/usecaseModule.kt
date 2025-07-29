package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.ClearSearchHistoryUseCase
import usecase.DeleteQueryFromHistoryUseCase
import usecase.GetMovieGenresUseCase
import usecase.GetMoviesByMoodUseCase
import usecase.GetPopularMoviesUseCase
import usecase.GetPopularTVShowsUseCase
import usecase.GetRecentHistoryUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.GetSeriesGenresUseCase
import usecase.GetTopRatedMoviesUseCase
import usecase.GetTopRatedSeriesUseCase
import usecase.GetUpComingMoviesUseCase
import usecase.SaveRecentHistoryUseCase
import usecase.SearchByActorNameUseCase
import usecase.SearchByCountryUseCase
import usecase.ValidatePasswordUseCase
import usecase.ValidateUsernameUseCase
import usecase.auth.IsLoggedInUseCase
import usecase.auth.LoginUseCase
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase
import usecase.mediadetails.AddContinueWatchingMovieUseCase
import usecase.mediadetails.AddContinueWatchingTVShowUseCase
import usecase.mediadetails.GetMovieCastUseCase
import usecase.mediadetails.GetMovieDetailsUseCase
import usecase.mediadetails.GetMovieGalleryUseCase
import usecase.mediadetails.GetMovieReviewUseCase
import usecase.mediadetails.GetSeasonEpisodesUseCase
import usecase.mediadetails.GetSeriesCastUseCase
import usecase.mediadetails.GetSeriesGalleryUseCase
import usecase.mediadetails.GetSeriesReviewUseCase
import usecase.mediadetails.GetSimilarMoviesUseCase
import usecase.mediadetails.GetSimilarSeriesUseCase
import usecase.mediadetails.GetTvShowDetailsUseCase

val useCaseModule = module {
    single { ValidateUsernameUseCase() }
    single { ValidatePasswordUseCase() }
    single { GetPopularMoviesUseCase(get()) }
    single { GetPopularTVShowsUseCase(get()) }
    single { SearchByCountryUseCase(get()) }
    single { SearchByActorNameUseCase(get()) }
    single { GetSearchMoviesUseCase(get()) }
    single { GetSearchTvShowsUseCase(get()) }
    single { GetRecentHistoryUseCase(get()) }
    single { SaveRecentHistoryUseCase(get()) }
    single { ClearSearchHistoryUseCase(get()) }
    single { DeleteQueryFromHistoryUseCase(get()) }
    single { GetSimilarMoviesUseCase(get()) }
    single { GetSimilarSeriesUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }
    single { GetTvShowDetailsUseCase(get()) }
    single { GetMovieGalleryUseCase(get()) }
    single { GetSeriesGalleryUseCase(get()) }
    single { GetMovieCastUseCase(get()) }
    single { GetSeriesCastUseCase(get()) }
    single { GetMovieReviewUseCase(get()) }
    single { GetSeriesReviewUseCase(get()) }
    single { GetSeasonEpisodesUseCase(get()) }
    single{ AddContinueWatchingMovieUseCase(get()) }
    single { GetContinueWatchingMovieUseCase(get()) }
    single { GetContinueWatchingTVShowUseCase(get()) }
    single { AddContinueWatchingTVShowUseCase(get()) }
    single { GetUpComingMoviesUseCase(get()) }
    single { GetMovieGenresUseCase(get()) }
    single { GetSeriesGenresUseCase(get()) }
    single { GetMoviesByMoodUseCase(get()) }

    single { LoginUseCase(get()) }
    single { IsLoggedInUseCase(get()) }
    single { GetTopRatedSeriesUseCase(get()) }
    single { GetTopRatedMoviesUseCase(get()) }

}