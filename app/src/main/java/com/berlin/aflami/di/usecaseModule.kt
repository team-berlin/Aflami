package com.berlin.aflami.di

import org.koin.dsl.module
import usecase.ClearSearchHistoryUseCase
import usecase.DeleteQueryFromHistoryUseCase
import usecase.GetRecentHistoryUseCase
import usecase.GetMovieDetailsUseCase
import usecase.GetMovieGalleryUseCase
import usecase.SearchByActorNameUseCase
import usecase.GetMovieCastUseCase
import usecase.GetMovieReviewUseCase
import usecase.GetSearchMoviesUseCase
import usecase.GetSearchTvShowsUseCase
import usecase.SaveRecentHistoryUseCase
import usecase.GetSeasonEpisodesUseCase
import usecase.GetSimilarMoviesUseCase
import usecase.GetSimilarSeriesUseCase
import usecase.GetTvShowDetailsUseCase
import usecase.GetSeriesGalleryUseCase
import usecase.GetSeriesCastUseCase
import usecase.GetSeriesReviewUseCase
import usecase.GetUpComingMoviesUseCase
import usecase.SearchByCountryUseCase

val useCaseModule = module {
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
    single { GetUpComingMoviesUseCase(get()) }

}