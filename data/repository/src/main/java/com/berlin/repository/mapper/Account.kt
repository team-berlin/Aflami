package com.berlin.repository.mapper

import com.berlin.entity.UserProfile
import com.berlin.repository.datasource.local.dto.UserProfileEntity
import com.berlin.repository.datasource.remote.dto.account.UserProfileDto
import com.berlin.repository.util.MediaUrls
import com.berlin.repository.util.gravatarUrl
import com.berlin.repository.util.tmdbImageUrl

fun UserProfileDto.toEntity(now: Long): UserProfileEntity {
    val nonNullId = requireNotNull(id) { "UserProfileDto.id is null" }
    return UserProfileEntity(
        id = nonNullId,
        username = username.orEmpty(),
        name = name.orEmpty(),
        avatarUrl = resolveAvatarUrl(this),
        includeAdult = includeAdult ?: false,
        updatedAt = now
    )
}

fun UserProfile.toEntity(now: Long): UserProfileEntity = UserProfileEntity(
    id = id,
    username = username,
    name = name,
    avatarUrl = avatarUrl,
    includeAdult = includeAdult,
    updatedAt = now
)

fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    username = username,
    name = name,
    avatarUrl = avatarUrl?:"",
    includeAdult = includeAdult
)

// (Optional)
fun UserProfileDto.toDomain(): UserProfile = UserProfile(
    id = requireNotNull(id) { "UserProfileDto.id is null" },
    username = username.orEmpty(),
    name = name.orEmpty(),
    includeAdult = includeAdult ?: false,
    avatarUrl = resolveAvatarUrl(this) ?: ""
)

private fun resolveAvatarUrl(dto: UserProfileDto): String? {
    val tmdbPath = dto.avatar?.tmdb?.avatarPath
    if (!tmdbPath.isNullOrBlank()) {
        return tmdbImageUrl(tmdbPath, MediaUrls.TmdbImageSize.W185)
    }
    return gravatarUrl(dto.avatar?.gravatar?.hash)
}