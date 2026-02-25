package com.studyswap.mobile.app.di;

import android.content.Context;
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideAppPreferenceDataStoreFactory implements Factory<AppPreferenceDataStore> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideAppPreferenceDataStoreFactory(AppModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public AppPreferenceDataStore get() {
    return provideAppPreferenceDataStore(module, contextProvider.get());
  }

  public static AppModule_ProvideAppPreferenceDataStoreFactory create(AppModule module,
      Provider<Context> contextProvider) {
    return new AppModule_ProvideAppPreferenceDataStoreFactory(module, contextProvider);
  }

  public static AppPreferenceDataStore provideAppPreferenceDataStore(AppModule instance,
      Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideAppPreferenceDataStore(context));
  }
}
