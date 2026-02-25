package com.studyswap.mobile.app.ux.sample;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class BaseViewModel_Factory implements Factory<BaseViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<GetBaseUiStateUseCase> getBaseUiStateUseCaseProvider;

  public BaseViewModel_Factory(Provider<Context> contextProvider,
      Provider<GetBaseUiStateUseCase> getBaseUiStateUseCaseProvider) {
    this.contextProvider = contextProvider;
    this.getBaseUiStateUseCaseProvider = getBaseUiStateUseCaseProvider;
  }

  @Override
  public BaseViewModel get() {
    return newInstance(contextProvider.get(), getBaseUiStateUseCaseProvider.get());
  }

  public static BaseViewModel_Factory create(Provider<Context> contextProvider,
      Provider<GetBaseUiStateUseCase> getBaseUiStateUseCaseProvider) {
    return new BaseViewModel_Factory(contextProvider, getBaseUiStateUseCaseProvider);
  }

  public static BaseViewModel newInstance(Context context,
      GetBaseUiStateUseCase getBaseUiStateUseCase) {
    return new BaseViewModel(context, getBaseUiStateUseCase);
  }
}
