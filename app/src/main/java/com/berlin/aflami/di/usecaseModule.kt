package com.berlin.aflami.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import repository.AppEntryRepository
import repository.AuthenticationRepository
import repository.MovieDetailsRepository
import repository.MovieRepository
import repository.TVShowDetailsRepository
import repository.TVShowRepository
import repository.UserFavouriteListRepository
import usecase.auth.GetLoginStatus
import usecase.auth.GetLoginUseCase
import usecase.auth.GetValidatePasswordUseCase
import usecase.auth.GetValidateUsernameUseCase
import usecase.favouritelist.CreateNewFavouriteListUseCase
import usecase.favouritelist.DeleteMovieFromUserFavouriteList
import usecase.favouritelist.DeleteUserFavouriteListUseCase
import usecase.favouritelist.EditListTitleUseCase
import usecase.favouritelist.GetAllFavouriteListsUseCase
import usecase.favouritelist.GetFavouriteListItemsUseCase
import usecase.mediadetails.GetMovieVideos
import usecase.movie.AddContinueWatchingMovieUseCase
import usecase.movie.ClearMoviesSearchHistoryUseCase
import usecase.movie.ContinueWatchingMovieUseCase
import usecase.movie.DeleteQueryFromMoviesHistoryUseCase
import usecase.movie.GetMovieCastUseCase
import usecase.movie.GetMovieDetailsUseCase
import usecase.movie.GetMovieGalleryUseCase
import usecase.movie.GetMovieGenresUseCase
import usecase.movie.GetMovieReviewUseCase
import usecase.movie.GetMoviesByMoodUseCase
import usecase.movie.GetPopularMoviesUseCase
import usecase.movie.GetRecentMoviesHistoryUseCase
import usecase.movie.GetSearchMoviesUseCase
import usecase.movie.GetSimilarMoviesUseCase
import usecase.movie.GetTopRatedMoviesUseCase
import usecase.movie.GetUpComingMoviesUseCase
import usecase.movie.SaveRecentMoviesHistoryUseCase
import usecase.movie.SearchByActorNameUseCase
import usecase.movie.SearchMoviesByCountryUseCase
import usecase.onboarding.GetFirstEntryUseCase
import usecase.onboarding.SaveFirstEntryUseCase
import usecase.tvshow.AddContinueWatchingTVShowUseCase
import usecase.tvshow.ContinueWatchingTVShowUseCase
import usecase.tvshow.GetPopularTVShowsUseCase
import usecase.tvshow.GetSearchTVShowsUseCase
import usecase.tvshow.GetSeasonEpisodesUseCase
import usecase.tvshow.GetSimilarTVShowsUseCase
import usecase.tvshow.GetTVShowCastUseCase
import usecase.tvshow.GetTVShowDetailsUseCase
import usecase.tvshow.GetTVShowGalleryUseCase
import usecase.tvshow.GetTVShowGenresUseCase
import usecase.tvshow.GetTVShowReviewUseCase
import usecase.tvshow.GetTVShowVideos
import usecase.tvshow.GetTopRatedTVShowUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideValidateUsernameUseCase(): GetValidateUsernameUseCase = GetValidateUsernameUseCase()

    @Provides
    fun provideValidatePasswordUseCase(): GetValidatePasswordUseCase = GetValidatePasswordUseCase()

    @Provides
    fun provideGetPopularMoviesUseCase(repository: MovieRepository): GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(repository)

    @Provides
    fun provideGetPopularTVShowsUseCase(repository: TVShowRepository): GetPopularTVShowsUseCase =
        GetPopularTVShowsUseCase(repository)

    @Provides
    fun provideSearchByCountryUseCase(repository: MovieRepository): SearchMoviesByCountryUseCase =
        SearchMoviesByCountryUseCase(repository)

    @Provides
    fun provideSearchByActorNameUseCase(repository: MovieRepository): SearchByActorNameUseCase =
        SearchByActorNameUseCase(repository)

    @Provides
    fun provideGetSearchMoviesUseCase(repository: MovieRepository): GetSearchMoviesUseCase =
        GetSearchMoviesUseCase(repository)

    @Provides
    fun provideGetSearchTvShowsUseCase(repository: TVShowRepository): GetSearchTVShowsUseCase =
        GetSearchTVShowsUseCase(repository)

    @Provides
    fun provideGetRecentHistoryUseCase(repository: MovieRepository): GetRecentMoviesHistoryUseCase =
        GetRecentMoviesHistoryUseCase(repository)

    @Provides
    fun provideSaveRecentHistoryUseCase(repository: MovieRepository): SaveRecentMoviesHistoryUseCase =
        SaveRecentMoviesHistoryUseCase(repository)

    @Provides
    fun provideClearSearchHistoryUseCase(repository: MovieRepository): ClearMoviesSearchHistoryUseCase =
        ClearMoviesSearchHistoryUseCase(repository)

    @Provides
    fun provideDeleteQueryFromHistoryUseCase(repository: MovieRepository): DeleteQueryFromMoviesHistoryUseCase =
        DeleteQueryFromMoviesHistoryUseCase(repository)

    @Provides
    fun provideGetSimilarMoviesUseCase(repository: MovieDetailsRepository): GetSimilarMoviesUseCase =
        GetSimilarMoviesUseCase(repository)

    @Provides
    fun provideGetSimilarSeriesUseCase(repository: TVShowDetailsRepository): GetSimilarTVShowsUseCase =
        GetSimilarTVShowsUseCase(repository)

    @Provides
    fun provideGetMovieDetailsUseCase(repository: MovieDetailsRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository)

    @Provides
    fun provideGetTvShowDetailsUseCase(repository: TVShowDetailsRepository): GetTVShowDetailsUseCase =
        GetTVShowDetailsUseCase(repository)

    @Provides
    fun provideGetMovieGalleryUseCase(repository: MovieDetailsRepository): GetMovieGalleryUseCase =
        GetMovieGalleryUseCase(repository)

    @Provides
    fun provideGetSeriesGalleryUseCase(repository: TVShowDetailsRepository): GetTVShowGalleryUseCase =
        GetTVShowGalleryUseCase(repository)

    @Provides
    fun provideGetMovieCastUseCase(repository: MovieDetailsRepository): GetMovieCastUseCase =
        GetMovieCastUseCase(repository)

    @Provides
    fun provideGetSeriesCastUseCase(repository: TVShowDetailsRepository): GetTVShowCastUseCase =
        GetTVShowCastUseCase(repository)

    @Provides
    fun provideGetMovieReviewUseCase(repository: MovieDetailsRepository): GetMovieReviewUseCase =
        GetMovieReviewUseCase(repository)

    @Provides
    fun provideGetSeriesReviewUseCase(repository: TVShowDetailsRepository): GetTVShowReviewUseCase =
        GetTVShowReviewUseCase(repository)

    @Provides
    fun provideGetSeasonEpisodesUseCase(repository: TVShowDetailsRepository): GetSeasonEpisodesUseCase =
        GetSeasonEpisodesUseCase(repository)

    @Provides
    fun provideAddContinueWatchingMovieUseCase(repository: MovieRepository): AddContinueWatchingMovieUseCase =
        AddContinueWatchingMovieUseCase(repository)

    @Provides
    fun provideGetContinueWatchingMovieUseCase(repository: MovieRepository): ContinueWatchingMovieUseCase =
        ContinueWatchingMovieUseCase(repository)

    @Provides
    fun provideGetContinueWatchingTVShowUseCase(repository: TVShowRepository): ContinueWatchingTVShowUseCase =
        ContinueWatchingTVShowUseCase(repository)

    @Provides
    fun provideAddContinueWatchingTVShowUseCase(repository: TVShowRepository): AddContinueWatchingTVShowUseCase =
        AddContinueWatchingTVShowUseCase(repository)

    @Provides
    fun provideGetUpComingMoviesUseCase(repository: MovieRepository): GetUpComingMoviesUseCase =
        GetUpComingMoviesUseCase(repository)

    @Provides
    fun provideGetMovieGenresUseCase(repository: MovieDetailsRepository): GetMovieGenresUseCase =
        GetMovieGenresUseCase(repository)

    @Provides
    fun provideGetSeriesGenresUseCase(repository: TVShowDetailsRepository): GetTVShowGenresUseCase =
        GetTVShowGenresUseCase(repository)

    @Provides
    fun provideGetMoviesByMoodUseCase(repository: MovieRepository): GetMoviesByMoodUseCase =
        GetMoviesByMoodUseCase(repository)

    @Provides
    fun provideGetMovieVideos(repository: MovieDetailsRepository): GetMovieVideos =
        GetMovieVideos(repository)

    @Provides
    fun provideGetTVShowVideos(repository: TVShowDetailsRepository): GetTVShowVideos =
        GetTVShowVideos(repository)

    @Provides
    fun provideLoginUseCase(repository: AuthenticationRepository): GetLoginUseCase =
        GetLoginUseCase(repository)

    @Provides
    fun provideIsLoggedInUseCase(repository: AuthenticationRepository): GetLoginStatus =
        GetLoginStatus(repository)

    @Provides
    fun provideGetTopRatedSeriesUseCase(repository: TVShowRepository): GetTopRatedTVShowUseCase =
        GetTopRatedTVShowUseCase(repository)

    @Provides
    fun provideGetTopRatedMoviesUseCase(repository: MovieRepository): GetTopRatedMoviesUseCase =
        GetTopRatedMoviesUseCase(repository)

    @Provides
    fun provideGetAppEntryUseCase(repository: AppEntryRepository): SaveFirstEntryUseCase =
        SaveFirstEntryUseCase(repository)

    @Provides
    fun provideGetFirstEntryUseCase(repository: AppEntryRepository): GetFirstEntryUseCase =
        GetFirstEntryUseCase(repository)

    @Provides
    fun provideCreateNewFavouriteListUseCase(userFavouriteListRepository: UserFavouriteListRepository) =
        CreateNewFavouriteListUseCase(userFavouriteListRepository)

    @Provides
    fun provideDeleteMovieFromUserFavouriteList(userFavouriteListRepository: UserFavouriteListRepository) =
        DeleteMovieFromUserFavouriteList(userFavouriteListRepository)

    @Provides
    fun provideDeleteUserFavouriteListUseCase(userFavouriteListRepository: UserFavouriteListRepository) =
        DeleteUserFavouriteListUseCase(userFavouriteListRepository)

    @Provides
    fun provideGetAllFavouriteListsUseCase(userFavouriteListRepository: UserFavouriteListRepository) =
        GetAllFavouriteListsUseCase(userFavouriteListRepository)

    @Provides
    fun provideGetFavouriteListItemsUseCase(userFavouriteListRepository: UserFavouriteListRepository) =
        GetFavouriteListItemsUseCase(userFavouriteListRepository)

    @Provides
    fun provideEditFavouriteListTitleUseCase(userFavouriteListRepository: UserFavouriteListRepository) =
        EditListTitleUseCase(userFavouriteListRepository)
}