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
        iso31661 = iso31661.orEmpty(),
        iso6391 = iso6391.orEmpty()
    )
}

fun UserProfile.toEntity(): UserProfileEntity = UserProfileEntity(
    id = id,
    username = username,
    name = name,
    avatarUrl = avatarUrl,
    includeAdult = includeAdult,
    iso31661 = iso31661,
    iso6391 = iso6391
)

fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    username = username,
    name = name,
    avatarUrl = avatarUrl,
    includeAdult = includeAdult,
    iso31661 = iso31661,
    iso6391 = iso6391
)