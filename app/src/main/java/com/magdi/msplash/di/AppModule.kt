package com.magdi.msplash.di

import com.magdi.msplash.data.PhotoMapper
import com.magdi.msplash.repo.PhotoRepo
import com.magdi.msplash.network.SplashAPI
import com.magdi.msplash.network.interceptor.HttpDebugInterceptor
import com.magdi.msplash.network.interceptor.SplashHeadersInterceptor
import com.magdi.msplash.usecase.LoadPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        api: SplashAPI,
        mapper: PhotoMapper
    ): PhotoRepo {
        return PhotoRepo(api, mapper)
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