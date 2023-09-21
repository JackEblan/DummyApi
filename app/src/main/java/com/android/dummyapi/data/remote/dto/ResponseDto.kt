package com.android.dummyapi.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ResponseDto(
    val limit: Int,
    val message: String,
    val offset: Int,
    val photos: List<PhotoDto>,
    val success: Boolean,
    @SerializedName("total_photos") val totalPhotos: Int
)