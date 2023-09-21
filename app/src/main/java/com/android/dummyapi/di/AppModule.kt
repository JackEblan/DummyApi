package com.android.dummyapi.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.android.dummyapi.data.local.PhotoEntity
import com.android.dummyapi.data.local.SlingDatabase
import com.android.dummyapi.data.remote.SlingApi
import com.android.dummyapi.data.remote.SlingApi.Companion.BASE_URL
import com.android.dummyapi.data.remote.SlingRemoteMediator
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): SlingDatabase {
        return Room.databaseBuilder(
            context, SlingDatabase::class.java, "sling.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideSlingApi(okHttpClient: OkHttpClient): SlingApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            .create()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager(
        slingDb: SlingDatabase, slingApi: SlingApi
    ): Pager<Int, PhotoEntity> {
        return Pager(config = PagingConfig(pageSize = 20), remoteMediator = SlingRemoteMediator(
            slingDb = slingDb, slingApi = slingApi

        ), pagingSourceFactory = {
            slingDb.dao.pagingSource()
        })
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}