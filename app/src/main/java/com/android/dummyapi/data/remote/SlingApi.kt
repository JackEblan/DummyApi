package com.android.dummyapi.data.remote

import com.android.dummyapi.data.remote.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SlingApi {

    @GET("photos")
    suspend fun getPhotos(@Query("offset") offset: Int, @Query("limit") limit: Int): ResponseDto

    companion object {
        const val BASE_URL = "https://api.slingacademy.com/v1/sample-data/"
    }
}