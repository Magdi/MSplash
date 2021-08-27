package com.magdi.msplash.di

import android.content.Context
import androidx.room.Room
import com.magdi.msplash.data.PhotoMapper
import com.magdi.msplash.db.AppDatabase
import com.magdi.msplash.repo.PhotoRepo
import com.magdi.msplash.network.SplashAPI
import com.magdi.msplash.network.interceptor.HttpDebugInterceptor
import com.magdi.msplash.network.interceptor.SplashHeadersInterceptor
import com.magdi.msplash.usecase.LoadPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePhotosMapper(): PhotoMapper {
        return PhotoMapper()
    }

    @Singleton
    @Provides
    fun providePhotoRepo(
        database: AppDatabase,
        api: SplashAPI,
        mapper: PhotoMapper
    ): PhotoRepo {
        return PhotoRepo(database.photosDao(), api, mapper)
    }

    @Singleton
    @Provides
    fun provideDB(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "photos-database").build()
    }


    @Singleton
    @Provides
    fun providesSplashAPI(baseUrl: String, httpClient: OkHttpClient): SplashAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
            .create(SplashAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SplashHeadersInterceptor())
            .addInterceptor(HttpDebugInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun baseURL(): String = "https://api.unsplash.com"

}