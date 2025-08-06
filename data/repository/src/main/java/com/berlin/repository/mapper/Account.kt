package com.berlin.repository.mapper

import com.berlin.entity.UserProfile
import com.berlin.repository.datasource.local.dto.UserProfileEntity
import com.berlin.repository.datasource.remote.dto.account.AccountDto

fun AccountDto.toDomain(): UserProfile {
    return UserProfile(
        id = id ?: 0,
        username = username.orEmpty(),
        name = name.orEmpty(),
        includeAdult = includeAdult ?: false,
        avatarUrl = avatar?.tmdb?.avatarPath?.let { "$POSTER_PREFIX$it" } ?: "",
        countryCodeIso31661 = countryCodeIso31661.orEmpty(),
        countryCodeIso6391 = countryCodeIso6391.orEmpty()
    )
}

fun UserProfile.toEntity(): UserProfileEntity = UserProfileEntity(
    id = id,
    username = username,
    name = name,
    avatarUrl = avatarUrl,
    includeAdult = includeAdult,
    countryCodeIso31661 = countryCodeIso31661,
    countryCodeIso6391 = countryCodeIso6391
)

fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    username = username,
    name = name,
    avatarUrl = avatarUrl,
    includeAdult = includeAdult,
    countryCodeIso31661 = countryCodeIso31661,
    countryCodeIso6391 = countryCodeIso6391
)