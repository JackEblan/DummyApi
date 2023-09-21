package com.android.dummyapi.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SlingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userPreviews: List<PhotoEntity>)

    @Query("SELECT * FROM PhotoEntity")
    fun pagingSource(): PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM PhotoEntity")
    suspend fun clearAll()
}