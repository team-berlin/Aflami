package com.berlin.repository.mapper

import com.berlin.entity.Video
import com.berlin.repository.datasource.remote.dto.details.VideoDto
import com.berlin.repository.util.youtubeUrl

fun VideoDto.toDomain():Video{
    return Video(
        videoUrl = youtubeUrl(this.key) ?: "",
        site = this.site?:"",
        videoType = this.type?:"",
        videoId =this.id?:"",
        videoLanguage = this.language?:""
    )
}