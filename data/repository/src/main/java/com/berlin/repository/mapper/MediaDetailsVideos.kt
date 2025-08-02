package com.berlin.repository.mapper

import com.berlin.entity.Video
import com.berlin.repository.datasource.remote.dto.details.VideoDto

fun VideoDto.toDomain():Video{
    return Video(
        videoUrl = (LINK_YOUTUBE+this.key),
        site = this.site?:"",
        videoType = this.type?:"",
        videoId =this.id?:"",
        videoLanguage = this.language?:""
    )
}

const val LINK_YOUTUBE="https://www.youtube.com/watch?v="