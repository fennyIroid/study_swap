package com.studyswap.mobile.app.di;

import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final NetworkModule module;

  private final Provider<AppPreferenceDataStore> appPreferenceDataStoreProvider;

  public NetworkModule_ProvideOkHttpClientFactory(NetworkModule module,
      Provider<AppPreferenceDataStore> appPreferenceDataStoreProvider) {
    this.module = module;
    this.appPreferenceDataStoreProvider = appPreferenceDataStoreProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(module, appPreferenceDataStoreProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(NetworkModule module,
      Provider<AppPreferenceDataStore> appPreferenceDataStoreProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(module, appPreferenceDataStoreProvider);
  }

  public static OkHttpClient provideOkHttpClient(NetworkModule instance,
      AppPreferenceDataStore appPreferenceDataStore) {
    return Preconditions.checkNotNullFromProvides(instance.provideOkHttpClient(appPreferenceDataStore));
  }
}
