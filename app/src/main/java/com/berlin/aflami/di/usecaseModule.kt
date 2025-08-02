package com.berlin.aflami.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import repository.AuthenticationRepository
import repository.ContinueWatchingRepository
import repository.HomeRepository
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.SearchRepository
import repository.TvShowDetailsRepository
import usecase.ClearSearchHistoryUseCase
import usecase.DeleteQueryFromHistoryUseCase
import usecase.GetMovieGenresUseCase
import usecase.GetMoviesByMoodUseCase
import usecase.GetPopularMoviesUseCase
import usecase.GetPopularTVShowsUseCase
import usecase.GetRecentHistoryUseCase
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
import usecase.mediadetails.GetMovieVideos
import usecase.mediadetails.GetSeasonEpisodesUseCase
import usecase.mediadetails.GetSeriesCastUseCase
import usecase.mediadetails.GetSeriesGalleryUseCase
import usecase.mediadetails.GetSeriesReviewUseCase
import usecase.mediadetails.GetSimilarMoviesUseCase
import usecase.mediadetails.GetSimilarSeriesUseCase
import usecase.mediadetails.GetTVShowVideos
import usecase.mediadetails.GetTvShowDetailsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideValidateUsernameUseCase(): ValidateUsernameUseCase = ValidateUsernameUseCase()

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase = ValidatePasswordUseCase()

    @Provides
    fun provideGetPopularMoviesUseCase(repository: MovieRepository): GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(repository)

    @Provides
    fun provideGetPopularTVShowsUseCase(repository: MovieRepository): GetPopularTVShowsUseCase =
        GetPopularTVShowsUseCase(repository)

    @Provides
    fun provideSearchByCountryUseCase(repository: SearchRepository): SearchByCountryUseCase =
        SearchByCountryUseCase(repository)

    @Provides
    fun provideSearchByActorNameUseCase(repository: SearchRepository): SearchByActorNameUseCase =
        SearchByActorNameUseCase(repository)

    @Provides
    fun provideGetSearchMoviesUseCase(repository: SearchRepository): GetSearchMoviesUseCase =
        GetSearchMoviesUseCase(repository)

    @Provides
    fun provideGetSearchTvShowsUseCase(repository: SearchRepository): GetSearchTvShowsUseCase =
        GetSearchTvShowsUseCase(repository)

    @Provides
    fun provideGetRecentHistoryUseCase(repository: SearchRepository): GetRecentHistoryUseCase =
        GetRecentHistoryUseCase(repository)

    @Provides
    fun provideSaveRecentHistoryUseCase(repository: SearchRepository): SaveRecentHistoryUseCase =
        SaveRecentHistoryUseCase(repository)

    @Provides
    fun provideClearSearchHistoryUseCase(repository: SearchRepository): ClearSearchHistoryUseCase =
        ClearSearchHistoryUseCase(repository)

    @Provides
    fun provideDeleteQueryFromHistoryUseCase(repository: SearchRepository): DeleteQueryFromHistoryUseCase =
        DeleteQueryFromHistoryUseCase(repository)

    @Provides
    fun provideGetSimilarMoviesUseCase(repository: MovieDetailsRepository): GetSimilarMoviesUseCase =
        GetSimilarMoviesUseCase(repository)

    @Provides
    fun provideGetSimilarSeriesUseCase(repository: TvShowDetailsRepository): GetSimilarSeriesUseCase =
        GetSimilarSeriesUseCase(repository)

    @Provides
    fun provideGetMovieDetailsUseCase(repository: MovieDetailsRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository)

    @Provides
    fun provideGetTvShowDetailsUseCase(repository: TvShowDetailsRepository): GetTvShowDetailsUseCase =
        GetTvShowDetailsUseCase(repository)

    @Provides
    fun provideGetMovieGalleryUseCase(repository: MovieDetailsRepository): GetMovieGalleryUseCase =
        GetMovieGalleryUseCase(repository)

    @Provides
    fun provideGetSeriesGalleryUseCase(repository: TvShowDetailsRepository): GetSeriesGalleryUseCase =
        GetSeriesGalleryUseCase(repository)

    @Provides
    fun provideGetMovieCastUseCase(repository: MovieDetailsRepository): GetMovieCastUseCase =
        GetMovieCastUseCase(repository)

    @Provides
    fun provideGetSeriesCastUseCase(repository: TvShowDetailsRepository): GetSeriesCastUseCase =
        GetSeriesCastUseCase(repository)

    @Provides
    fun provideGetMovieReviewUseCase(repository: MovieDetailsRepository): GetMovieReviewUseCase =
        GetMovieReviewUseCase(repository)

    @Provides
    fun provideGetSeriesReviewUseCase(repository: TvShowDetailsRepository): GetSeriesReviewUseCase =
        GetSeriesReviewUseCase(repository)

    @Provides
    fun provideGetSeasonEpisodesUseCase(repository: TvShowDetailsRepository): GetSeasonEpisodesUseCase =
        GetSeasonEpisodesUseCase(repository)

    @Provides
    fun provideAddContinueWatchingMovieUseCase(repository: ContinueWatchingRepository): AddContinueWatchingMovieUseCase =
        AddContinueWatchingMovieUseCase(repository)

    @Provides
    fun provideGetContinueWatchingMovieUseCase(repository: ContinueWatchingRepository): GetContinueWatchingMovieUseCase =
        GetContinueWatchingMovieUseCase(repository)

    @Provides
    fun provideGetContinueWatchingTVShowUseCase(repository: ContinueWatchingRepository): GetContinueWatchingTVShowUseCase =
        GetContinueWatchingTVShowUseCase(repository)

    @Provides
    fun provideAddContinueWatchingTVShowUseCase(repository: ContinueWatchingRepository): AddContinueWatchingTVShowUseCase =
        AddContinueWatchingTVShowUseCase(repository)

    @Provides
    fun provideGetUpComingMoviesUseCase(repository: MovieRepository): GetUpComingMoviesUseCase =
        GetUpComingMoviesUseCase(repository)

    @Provides
    fun provideGetMovieGenresUseCase(repository: MovieDetailsRepository): GetMovieGenresUseCase =
        GetMovieGenresUseCase(repository)

    @Provides
    fun provideGetSeriesGenresUseCase(repository: TvShowDetailsRepository): GetSeriesGenresUseCase =
        GetSeriesGenresUseCase(repository)

    @Provides
    fun provideGetMoviesByMoodUseCase(repository: MovieRepository): GetMoviesByMoodUseCase =
        GetMoviesByMoodUseCase(repository)

    @Provides
    fun provideGetMovieVideos(repository: MovieDetailsRepository): GetMovieVideos =
        GetMovieVideos(repository)

    @Provides
    fun provideGetTVShowVideos(repository: TvShowDetailsRepository): GetTVShowVideos =
        GetTVShowVideos(repository)

    @Provides
    fun provideLoginUseCase(repository: AuthenticationRepository): LoginUseCase =
        LoginUseCase(repository)

    @Provides
    fun provideIsLoggedInUseCase(repository: AuthenticationRepository): IsLoggedInUseCase =
        IsLoggedInUseCase(repository)

    @Provides
    fun provideGetTopRatedSeriesUseCase(repository: HomeRepository): GetTopRatedSeriesUseCase =
        GetTopRatedSeriesUseCase(repository)

    @Provides
    fun provideGetTopRatedMoviesUseCase(repository: HomeRepository): GetTopRatedMoviesUseCase =
        GetTopRatedMoviesUseCase(repository)
}