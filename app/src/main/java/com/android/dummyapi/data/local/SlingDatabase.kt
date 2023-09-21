package com.android.dummyapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PhotoEntity::class], version = 1
)
abstract class SlingDatabase : RoomDatabase() {

    abstract val dao: SlingDao
}