package com.studyswap.mobile.app.di;

import com.studyswap.mobile.app.data.source.remote.repository.ApiServices;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class NetworkModule_ProvideApiServicesFactory implements Factory<ApiServices> {
  private final NetworkModule module;

  private final Provider<Retrofit.Builder> retrofitBuilderProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvideApiServicesFactory(NetworkModule module,
      Provider<Retrofit.Builder> retrofitBuilderProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    this.module = module;
    this.retrofitBuilderProvider = retrofitBuilderProvider;
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public ApiServices get() {
    return provideApiServices(module, retrofitBuilderProvider.get(), okHttpClientProvider.get());
  }

  public static NetworkModule_ProvideApiServicesFactory create(NetworkModule module,
      Provider<Retrofit.Builder> retrofitBuilderProvider,
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideApiServicesFactory(module, retrofitBuilderProvider, okHttpClientProvider);
  }

  public static ApiServices provideApiServices(NetworkModule instance,
      Retrofit.Builder retrofitBuilder, OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(instance.provideApiServices(retrofitBuilder, okHttpClient));
  }
}
