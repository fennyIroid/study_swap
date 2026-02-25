package com.studyswap.mobile.app.di

import android.util.Log
import com.studyswap.mobile.app.BuildConfig
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.EndPoints
import com.studyswap.mobile.app.data.source.remote.interceptor.AuthInterceptor
import com.studyswap.mobile.app.data.source.remote.interceptor.RequestInterceptor
import com.studyswap.mobile.app.data.source.remote.repository.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor { message -> Log.d("Retrofit", message) }
            .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(EndPoints.URLs.BASE_URL)

    @Singleton
    @Provides
    fun provideOkHttpClient(appPreferenceDataStore: AppPreferenceDataStore): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(RequestInterceptor(appPreferenceDataStore))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

    @Singleton
    @Provides
    fun provideApiServices(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): ApiServices =
        retrofitBuilder.client(okHttpClient).build().create(ApiServices::class.java)
}
