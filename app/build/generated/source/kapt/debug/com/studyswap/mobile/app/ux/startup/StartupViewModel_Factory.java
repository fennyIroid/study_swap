package com.studyswap.mobile.app.ux.startup;

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
public final class StartupViewModel_Factory implements Factory<StartupViewModel> {
  @Override
  public StartupViewModel get() {
    return newInstance();
  }

  public static StartupViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static StartupViewModel newInstance() {
    return new StartupViewModel();
  }

  private static final class InstanceHolder {
    private static final StartupViewModel_Factory INSTANCE = new StartupViewModel_Factory();
  }
}
