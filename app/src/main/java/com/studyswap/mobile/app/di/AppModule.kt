package com.studyswap.mobile.app.di

import android.content.Context
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepositoryImpl
import com.studyswap.mobile.app.data.source.remote.repository.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiRepository(apiServices: ApiServices): ApiRepository =
        ApiRepositoryImpl(apiServices)

    @Provides
    @Singleton
    fun provideAppPreferenceDataStore(@ApplicationContext context: Context): AppPreferenceDataStore =
        AppPreferenceDataStore(context)
}
