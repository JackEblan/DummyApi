package com.android.dummyapi.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.android.dummyapi.data.remote.dto.PhotoDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@ProvidedTypeConverter
class PhotoTypeConverter @Inject constructor(private val gson: Gson) {

    @TypeConverter
    fun fromPhotoList(photoDtoList: List<PhotoDto>): String {
        val type = object : TypeToken<List<PhotoDto>>() {}.type
        return gson.toJson(photoDtoList, type)
    }

    @TypeConverter
    fun toPhotoList(photoListString: String): List<PhotoDto> {
        val type = object : TypeToken<List<PhotoDto>>() {}.type
        return gson.fromJson(photoListString, type)
    }
}