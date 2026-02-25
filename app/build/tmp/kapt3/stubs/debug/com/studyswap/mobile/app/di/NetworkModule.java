package com.studyswap.mobile.app.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0007J\b\u0010\u000e\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/studyswap/mobile/app/di/NetworkModule;", "", "()V", "httpLoggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "provideApiServices", "Lcom/studyswap/mobile/app/data/source/remote/repository/ApiServices;", "retrofitBuilder", "Lretrofit2/Retrofit$Builder;", "okHttpClient", "Lokhttp3/OkHttpClient;", "provideOkHttpClient", "appPreferenceDataStore", "Lcom/studyswap/mobile/app/data/source/local/datastore/AppPreferenceDataStore;", "provideRetrofitBuilder", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class NetworkModule {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.logging.HttpLoggingInterceptor httpLoggingInterceptor = null;
    
    public NetworkModule() {
        super();
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit.Builder provideRetrofitBuilder() {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOkHttpClient(@org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore appPreferenceDataStore) {
        return null;
    }
    
    @javax.inject.Singleton()
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.studyswap.mobile.app.data.source.remote.repository.ApiServices provideApiServices(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit.Builder retrofitBuilder, @org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient) {
        return null;
    }
}