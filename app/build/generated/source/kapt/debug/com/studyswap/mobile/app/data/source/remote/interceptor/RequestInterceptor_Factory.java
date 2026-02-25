package com.studyswap.mobile.app.data.source.remote.interceptor;

import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore;
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
public final class RequestInterceptor_Factory implements Factory<RequestInterceptor> {
  private final Provider<AppPreferenceDataStore> appPreferenceDataStoreProvider;

  public RequestInterceptor_Factory(
      Provider<AppPreferenceDataStore> appPreferenceDataStoreProvider) {
    this.appPreferenceDataStoreProvider = appPreferenceDataStoreProvider;
  }

  @Override
  public RequestInterceptor get() {
    return newInstance(appPreferenceDataStoreProvider.get());
  }

  public static RequestInterceptor_Factory create(
      Provider<AppPreferenceDataStore> appPreferenceDataStoreProvider) {
    return new RequestInterceptor_Factory(appPreferenceDataStoreProvider);
  }

  public static RequestInterceptor newInstance(AppPreferenceDataStore appPreferenceDataStore) {
    return new RequestInterceptor(appPreferenceDataStore);
  }
}
