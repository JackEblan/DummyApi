package com.android.dummyapi.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.dummyapi.data.local.PhotoEntity
import com.android.dummyapi.data.local.SlingDatabase
import com.android.dummyapi.data.mappers.toPhotoEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class SlingRemoteMediator(
    private val slingDb: SlingDatabase, private val slingApi: SlingApi
) : RemoteMediator<Int, PhotoEntity>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0
                    } else {
                        lastItem.id + 1
                    }
                }
            }

            val response = slingApi.getPhotos(
                offset = offset, limit = state.config.pageSize
            )

            slingDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    slingDb.dao.clearAll()
                }

                val photoEntities = response.photos.map { it.toPhotoEntity() }
                slingDb.dao.insertAll(photoEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.photos.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}