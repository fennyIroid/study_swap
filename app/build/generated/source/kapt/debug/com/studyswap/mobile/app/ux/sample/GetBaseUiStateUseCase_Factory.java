package com.studyswap.mobile.app.ux.sample;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class GetBaseUiStateUseCase_Factory implements Factory<GetBaseUiStateUseCase> {
  @Override
  public GetBaseUiStateUseCase get() {
    return newInstance();
  }

  public static GetBaseUiStateUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GetBaseUiStateUseCase newInstance() {
    return new GetBaseUiStateUseCase();
  }

  private static final class InstanceHolder {
    private static final GetBaseUiStateUseCase_Factory INSTANCE = new GetBaseUiStateUseCase_Factory();
  }
}
