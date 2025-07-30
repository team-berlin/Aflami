package com.berlin.aflami.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import usecase.ClearSearchHistoryUseCase
import usecase.DeleteQueryFromHistoryUseCase
import usecase.GetMovieGenresUseCase
import usecase.GetRecentHistoryUseCase
import usecase.GetPopularTVShowsUseCase
import usecase.GetPopularMoviesUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.SaveRecentHistoryUseCase
import usecase.GetSeriesGenresUseCase
import usecase.GetTopRatedMoviesUseCase
import usecase.GetTopRatedSeriesUseCase
import usecase.SearchByActorNameUseCase
import usecase.GetUpComingMoviesUseCase
import usecase.mediadetails.GetSeasonEpisodesUseCase
import usecase.mediadetails.GetSimilarMoviesUseCase
import usecase.mediadetails.GetSimilarSeriesUseCase
import usecase.mediadetails.GetTvShowDetailsUseCase
import usecase.mediadetails.GetSeriesGalleryUseCase
import usecase.mediadetails.GetSeriesCastUseCase
import usecase.mediadetails.GetSeriesReviewUseCase
import usecase.SearchByCountryUseCase
import usecase.home.GetContinueWatchingMovieUseCase
import usecase.home.GetContinueWatchingTVShowUseCase
import usecase.mediadetails.AddContinueWatchingMovieUseCase
import usecase.mediadetails.AddContinueWatchingTVShowUseCase
import usecase.mediadetails.GetMovieCastUseCase
import usecase.mediadetails.GetMovieDetailsUseCase
import usecase.mediadetails.GetMovieGalleryUseCase
import usecase.mediadetails.GetMovieReviewUseCase
import usecase.ValidatePasswordUseCase
import usecase.ValidateUsernameUseCase
import usecase.auth.IsLoggedInUseCase
import usecase.auth.LoginUseCase

val useCaseModule = module {
    factoryOf(::ValidateUsernameUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::GetPopularMoviesUseCase)
    factoryOf(::GetPopularTVShowsUseCase)
    factoryOf(::SearchByCountryUseCase)
    factoryOf(::SearchByActorNameUseCase)
    factoryOf(::GetSearchMoviesUseCase)
    factoryOf(::GetSearchTvShowsUseCase)
    factoryOf(::GetRecentHistoryUseCase)
    factoryOf(::SaveRecentHistoryUseCase)
    factoryOf(::ClearSearchHistoryUseCase)
    factoryOf(::DeleteQueryFromHistoryUseCase)
    factoryOf(::GetSimilarMoviesUseCase)
    factoryOf(::GetSimilarSeriesUseCase)
    factoryOf(::GetMovieDetailsUseCase)
    factoryOf(::GetTvShowDetailsUseCase)
    factoryOf(::GetMovieGalleryUseCase)
    factoryOf(::GetSeriesGalleryUseCase)
    factoryOf(::GetMovieCastUseCase)
    factoryOf(::GetSeriesCastUseCase)
    factoryOf(::GetMovieReviewUseCase)
    factoryOf(::GetSeriesReviewUseCase)
    factoryOf(::GetSeasonEpisodesUseCase)
    factoryOf(::AddContinueWatchingMovieUseCase)
    factoryOf(::GetContinueWatchingMovieUseCase)
    factoryOf(::GetContinueWatchingTVShowUseCase)
    factoryOf(::AddContinueWatchingTVShowUseCase)
    factoryOf(::GetUpComingMoviesUseCase)
    factoryOf(::GetMovieGenresUseCase)
    factoryOf(::GetSeriesGenresUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::IsLoggedInUseCase)
    factoryOf(::GetTopRatedSeriesUseCase)
    factoryOf(::GetTopRatedMoviesUseCase)


}