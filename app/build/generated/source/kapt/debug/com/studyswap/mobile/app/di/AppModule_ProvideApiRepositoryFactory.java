package com.studyswap.mobile.app.di;

import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository;
import com.studyswap.mobile.app.data.source.remote.repository.ApiServices;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideApiRepositoryFactory implements Factory<ApiRepository> {
  private final AppModule module;

  private final Provider<ApiServices> apiServicesProvider;

  public AppModule_ProvideApiRepositoryFactory(AppModule module,
      Provider<ApiServices> apiServicesProvider) {
    this.module = module;
    this.apiServicesProvider = apiServicesProvider;
  }

  @Override
  public ApiRepository get() {
    return provideApiRepository(module, apiServicesProvider.get());
  }

  public static AppModule_ProvideApiRepositoryFactory create(AppModule module,
      Provider<ApiServices> apiServicesProvider) {
    return new AppModule_ProvideApiRepositoryFactory(module, apiServicesProvider);
  }

  public static ApiRepository provideApiRepository(AppModule instance, ApiServices apiServices) {
    return Preconditions.checkNotNullFromProvides(instance.provideApiRepository(apiServices));
  }
}
