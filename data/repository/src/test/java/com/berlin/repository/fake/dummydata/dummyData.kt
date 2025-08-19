package com.berlin.repository.fake.dummydata

import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.entity.Season
import com.berlin.entity.TVShow
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.HomeMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedTvShowEntity
import com.berlin.repository.datasource.local.dto.HomeSection
import com.berlin.repository.datasource.local.dto.HomeTVShowEntity
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.TVShowDetailsDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.util.Constants.ACTING_DEPARTMENT
import com.berlin.repository.util.Constants.MOVIE_MEDIA_TYPE

object DummyData {

    // ===== Movies Entities =====
    val recentlyWatchedMovieEntity = RecentlyWatchedMovieEntity(
        id = 1,
        title = "Recently Watched",
        rating = 8.5,
        releaseDate = "2024-05-01",
        posterURL = "/recent.jpg",
        screenShot = "/recent_screen.jpg",
        description = "A recently watched movie description",
        genres = listOf(1,2),
        duration = 120,
        hasVideo = true,
        productionCompanies = listOf("Berlin Productions"),
        originCountry = "US",
        galleryUrl = listOf("/gallery1.jpg", "/gallery2.jpg"),
        reviews = listOf("Nice movie", "Loved it")
    )

    val movieEntity = Movie(
        id = 1,
        title = "Test Movie",
        rating = 7.5,
        releaseDate = "2023-01-01",
        posterURL = "/poster.jpg",
        screenShot = "/screenshot.jpg",
        description = "Some description",
        genres = listOf(Genre(
            id = 1,
            name = "Comedy"
        )),
        duration = 110,
        hasVideo = false,
        companyProductions = listOf(
            CompanyProduction(
                id = 1,
                name = "Production Company",
                posterURL ="/poster.jpg",
                originCountry = "EG"
            )
        ),
        originCountry = "DE",
        galleryUrl = listOf("/gallery1.jpg"),
        reviews = listOf(Review(
            id ="1",
            name = "Nada",
            userName ="Nada20",
            avatarImage = "/avatar.jpg",
            rating = 10.0,
            content = "This is a review",
            date = "2023-01-01"
        )),
        isFavourite = false
    )

    // ==== TVShow Entity =====
    val recentlyWatchedTvShowEntity = RecentlyWatchedTvShowEntity(
        id = 1,
        title = "Recently Watched TV Show",
        rating = 8.9,
        posterURL = "/recent_tv.jpg",
        releaseDate = "2024-07-01",
        screenShot = "/recent_tv_screen.jpg",
        description = "A recently watched TV show description",
        genres = listOf(1, 2),
        duration = 45,
        hasVideo = true,
        productionCompanies = listOf("Berlin TV Productions"),
        originCountry = "US",
        seasons = listOf("Season 1", "Season 2"),
        galleryUrl = listOf("/gallery_tv1.jpg", "/gallery_tv2.jpg"),
        reviews = listOf("Amazing show!", "Binge-worthy")
    )

    val tvShowEntity = TVShow(
        id = 1,
        title = "Test TV Show",
        rating = 9.5,
        posterURL = "/tv_poster.jpg",
        releaseDate = "2023-02-15",
        screenShot = "/tv_screenshot.jpg",
        description = "A thrilling drama TV show.",
        genres = listOf(Genre(id = 1, name = "Drama")),
        duration = 50,
        companyProductions = listOf(
            CompanyProduction(
                id = 1,
                name = "Drama Productions",
                posterURL = "/company_tv.jpg",
                originCountry = "US"
            )
        ),
        originCountry = "US",
        numberOfSeasons = 3,
        hasVideo = false,
        galleryUrl = listOf("/tv_gallery1.jpg", "/tv_gallery2.jpg")
    )

    // ===== Example Season =====
    val sampleSeason = Season(
        id = 1,
        episodeCount = 10,
        episodes = emptyList(),
        name = "Season 1",
        description = "First season of the show",
        posterURL = "/season1_poster.jpg",
        seasonNumber = 1
    )

    //===== Movie =====
    val homeMovieEntity = HomeMovieEntity(
        id = 1,
        title = "Local Movie",
        homeSection = HomeSection.TOP_RATING,
        rating = "8.9",
        releaseYear = "2022",
        genre = listOf(1,2),
        poster = "/poster_top.jpg"
    )
    val movieHomeEntityTopRated = homeMovieEntity.copy(
        homeSection = HomeSection.TOP_RATING,
        title = "Top Rated Movie"
    )

    val movieHomeEntityUpcoming = homeMovieEntity.copy(
        homeSection = HomeSection.UPCOMING,
        title = "Upcoming Movie"
    )

    val movieHomeEntityPopular = homeMovieEntity.copy(
        homeSection = HomeSection.POPULAR,
        title = "Popular Movie"
    )

    // ===== Preferences =====
    val mediaPreferencesList = listOf(
        CategoriesPreferencesEntity(categoryId = 101, count = 5),
        CategoriesPreferencesEntity(categoryId = 102, count = 1)
    )

    // ===== Remote Responses =====
    val movieDetailsDto = MovieDetailsDto(
        id = 1,
        title = "Remote Movie",
        mediaType = MOVIE_MEDIA_TYPE,
    )

    val baseResponseMovieDetails = BaseResponse(
        results = listOf(movieDetailsDto),
    )

    val personDto = PersonDto(
        id = 1,
        name = "Ahmed Helmy",
        profilePath = "/ahmed.jpg",
        knownForDepartment = ACTING_DEPARTMENT,
        knownFor = listOf(movieDetailsDto)
    )

    val baseResponsePersonDto = BaseResponse(
        results = listOf(personDto),
    )


    //==== TVShow =====

    val homeTVShowEntity = HomeTVShowEntity(
        id = 1,
        title = "Local TV Show",
        homeSection = HomeSection.TOP_RATING,
        rating = "9.0",
        releaseYear = "2023",
        genre = listOf(1, 2),
        poster = "/poster_top_tv.jpg"
    )

    val tvShowHomeEntityTopRated = homeTVShowEntity.copy(
        homeSection = HomeSection.TOP_RATING,
        title = "Top Rated TV Show"
    )

    val tvShowHomeEntityUpcoming = homeTVShowEntity.copy(
        homeSection = HomeSection.UPCOMING,
        title = "Upcoming TV Show"
    )

    val tvShowHomeEntityPopular = homeTVShowEntity.copy(
        homeSection = HomeSection.POPULAR,
        title = "Popular TV Show"
    )

    // ===== Remote Responses =====
    val tvShowDetailsDto = TVShowDetailsDto(
        id = 1,
        name = "Remote TV Show",
        numberOfEpisodes = 10,
        numberOfSeasons = 2
        )

    val baseResponseTVShowDetails = BaseResponse(
        results = listOf(tvShowDetailsDto),
    )
}
