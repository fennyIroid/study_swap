package com.studyswap.mobile.app.data.source.remote.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ApiRepositoryImpl_Factory implements Factory<ApiRepositoryImpl> {
  private final Provider<ApiServices> apiServicesProvider;

  public ApiRepositoryImpl_Factory(Provider<ApiServices> apiServicesProvider) {
    this.apiServicesProvider = apiServicesProvider;
  }

  @Override
  public ApiRepositoryImpl get() {
    return newInstance(apiServicesProvider.get());
  }

  public static ApiRepositoryImpl_Factory create(Provider<ApiServices> apiServicesProvider) {
    return new ApiRepositoryImpl_Factory(apiServicesProvider);
  }

  public static ApiRepositoryImpl newInstance(ApiServices apiServices) {
    return new ApiRepositoryImpl(apiServices);
  }
}
