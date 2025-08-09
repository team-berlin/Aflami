package com.berlin.repository.fake.dummydata

import com.berlin.entity.CompanyProduction
import com.berlin.entity.Genre
import com.berlin.entity.Movie
import com.berlin.entity.Review
import com.berlin.repository.datasource.local.dto.CategoriesPreferencesEntity
import com.berlin.repository.datasource.local.dto.MovieHomeEntity
import com.berlin.repository.datasource.local.dto.RecentlyWatchedMovieEntity
import com.berlin.repository.datasource.local.dto.SectionHome
import com.berlin.repository.datasource.remote.dto.PersonDto
import com.berlin.repository.datasource.remote.dto.movie.MovieDetailsDto
import com.berlin.repository.datasource.remote.response.BaseResponse
import com.berlin.repository.util.Constants.ACTING_DEPARTMENT

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

    val movieHomeEntity = MovieHomeEntity(
        id = 1,
        title = "Local Movie",
        addedAt = System.currentTimeMillis(),
        sectionHome = SectionHome.TOP_RATING,
        rating = "8.9",
        releaseYear = "2022",
        genre = listOf(1,2),
        poster = "/poster_top.jpg"
    )
    val movieHomeEntityTopRated = movieHomeEntity.copy(
        sectionHome = SectionHome.TOP_RATING,
        title = "Top Rated Movie"
    )

    val movieHomeEntityUpcoming = movieHomeEntity.copy(
        sectionHome = SectionHome.UPCOMING,
        title = "Upcoming Movie"
    )

    val movieHomeEntityPopular = movieHomeEntity.copy(
        sectionHome = SectionHome.POPULAR,
        title = "Popular Movie"
    )

    // ===== Preferences =====
    val preferencesList = listOf(
        CategoriesPreferencesEntity(categoryId = 101, count = 5),
        CategoriesPreferencesEntity(categoryId = 102, count = 1)
    )

    // ===== Remote Responses =====
    val movieDetailsDto = MovieDetailsDto(
        id = 1,
        title = "Remote Movie",
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
}
