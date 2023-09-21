package com.android.dummyapi.data.mappers

import com.android.dummyapi.data.local.PhotoEntity
import com.android.dummyapi.data.remote.dto.PhotoDto
import com.android.dummyapi.domain.Photo

fun PhotoDto.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = id, description = description, title = title, url = url, user = user
    )
}

fun PhotoEntity.toPhoto(): Photo {
    return Photo(
        description = description, title = title, url = url
    )
}