package com.android.dummyapi.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: Int
)