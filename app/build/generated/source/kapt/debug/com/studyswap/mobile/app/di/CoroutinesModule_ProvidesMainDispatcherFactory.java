package com.studyswap.mobile.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata
@QualifierMetadata("com.studyswap.mobile.app.di.MainDispatcher")
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
public final class CoroutinesModule_ProvidesMainDispatcherFactory implements Factory<CoroutineDispatcher> {
  @Override
  public CoroutineDispatcher get() {
    return providesMainDispatcher();
  }

  public static CoroutinesModule_ProvidesMainDispatcherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineDispatcher providesMainDispatcher() {
    return Preconditions.checkNotNullFromProvides(CoroutinesModule.INSTANCE.providesMainDispatcher());
  }

  private static final class InstanceHolder {
    private static final CoroutinesModule_ProvidesMainDispatcherFactory INSTANCE = new CoroutinesModule_ProvidesMainDispatcherFactory();
  }
}
