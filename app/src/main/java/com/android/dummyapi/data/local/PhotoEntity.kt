package com.android.dummyapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val description: String,
    val title: String,
    val url: String,
    val user: Int
)