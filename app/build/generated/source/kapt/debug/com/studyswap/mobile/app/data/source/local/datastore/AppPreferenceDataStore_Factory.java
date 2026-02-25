package com.studyswap.mobile.app.data.source.local.datastore;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppPreferenceDataStore_Factory implements Factory<AppPreferenceDataStore> {
  private final Provider<Context> applicationContextProvider;

  public AppPreferenceDataStore_Factory(Provider<Context> applicationContextProvider) {
    this.applicationContextProvider = applicationContextProvider;
  }

  @Override
  public AppPreferenceDataStore get() {
    return newInstance(applicationContextProvider.get());
  }

  public static AppPreferenceDataStore_Factory create(
      Provider<Context> applicationContextProvider) {
    return new AppPreferenceDataStore_Factory(applicationContextProvider);
  }

  public static AppPreferenceDataStore newInstance(Context applicationContext) {
    return new AppPreferenceDataStore(applicationContext);
  }
}
