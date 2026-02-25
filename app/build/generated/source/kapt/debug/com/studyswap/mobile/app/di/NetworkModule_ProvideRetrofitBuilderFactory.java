package com.studyswap.mobile.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
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
public final class NetworkModule_ProvideRetrofitBuilderFactory implements Factory<Retrofit.Builder> {
  private final NetworkModule module;

  public NetworkModule_ProvideRetrofitBuilderFactory(NetworkModule module) {
    this.module = module;
  }

  @Override
  public Retrofit.Builder get() {
    return provideRetrofitBuilder(module);
  }

  public static NetworkModule_ProvideRetrofitBuilderFactory create(NetworkModule module) {
    return new NetworkModule_ProvideRetrofitBuilderFactory(module);
  }

  public static Retrofit.Builder provideRetrofitBuilder(NetworkModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideRetrofitBuilder());
  }
}
