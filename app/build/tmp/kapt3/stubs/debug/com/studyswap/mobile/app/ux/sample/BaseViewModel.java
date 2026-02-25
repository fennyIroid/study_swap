package com.studyswap.mobile.app.ux.sample;

/**
 * Sample template: ViewModel that exposes UI state and handles navigation.
 * Copy and rename (e.g. HomeViewModel), implement ViewModelNav by ViewModelNavImpl() when screen navigates.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0096\u0001J#\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0096\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u001eJ4\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0017\u0010\u001f\u001a\u0013\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00120 \u00a2\u0006\u0002\b\"H\u0096\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010#J%\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0096\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010$J\u0011\u0010\u0011\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\nH\u0096\u0001J\u0017\u0010\u0011\u001a\u00020\u00122\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001a0\'H\u0096\u0001J)\u0010\u0017\u001a\u00020\u00122\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\b\b\u0002\u0010*\u001a\u00020\u0018H\u0096\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b+\u0010$J7\u0010,\u001a\u00020\u00122\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\'2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\b\b\u0002\u0010*\u001a\u00020\u0018H\u0096\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b/\u00100J\u0011\u00101\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\nH\u0096\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0096\u0005\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00062"}, d2 = {"Lcom/studyswap/mobile/app/ux/sample/BaseViewModel;", "Landroidx/lifecycle/ViewModel;", "Lcom/studyswap/mobile/app/navigation/ViewModelNav;", "context", "Landroid/content/Context;", "getBaseUiStateUseCase", "Lcom/studyswap/mobile/app/ux/sample/GetBaseUiStateUseCase;", "(Landroid/content/Context;Lcom/studyswap/mobile/app/ux/sample/GetBaseUiStateUseCase;)V", "navigationActionFlow", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "getNavigationActionFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "Lcom/studyswap/mobile/app/ux/sample/BaseUiState;", "getUiState", "()Lcom/studyswap/mobile/app/ux/sample/BaseUiState;", "navigate", "", "intent", "Landroid/content/Intent;", "options", "Landroid/os/Bundle;", "popBackStack", "", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "navOptions", "Landroidx/navigation/NavOptions;", "navigate-9zFSldk", "(Ljava/lang/String;Landroidx/navigation/NavOptions;)V", "optionsBuilder", "Lkotlin/Function1;", "Landroidx/navigation/NavOptionsBuilder;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "(Ljava/lang/String;Z)V", "navigationAction", "routes", "", "popToRouteDefinition", "Lcom/studyswap/mobile/app/navigation/NavRouteDefinition;", "inclusive", "popBackStack-EcEeuvA", "popBackStackWithResult", "resultValues", "Lcom/studyswap/mobile/app/navigation/PopResultKeyValue;", "popBackStackWithResult-xTfWKlU", "(Ljava/util/List;Ljava/lang/String;Z)V", "resetNavigate", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BaseViewModel extends androidx.lifecycle.ViewModel implements com.studyswap.mobile.app.navigation.ViewModelNav {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.studyswap.mobile.app.ux.sample.GetBaseUiStateUseCase getBaseUiStateUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.studyswap.mobile.app.ux.sample.BaseUiState uiState = null;
    
    @javax.inject.Inject()
    public BaseViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.ux.sample.GetBaseUiStateUseCase getBaseUiStateUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.studyswap.mobile.app.ux.sample.BaseUiState getUiState() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.StateFlow<com.studyswap.mobile.app.navigation.NavigationAction> getNavigationActionFlow() {
        return null;
    }
    
    @java.lang.Override()
    public void navigate(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, @org.jetbrains.annotations.Nullable()
    android.os.Bundle options, boolean popBackStack) {
    }
    
    @java.lang.Override()
    public void navigate(@org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.navigation.NavigationAction navigationAction) {
    }
    
    @java.lang.Override()
    public void navigate(@org.jetbrains.annotations.NotNull()
    java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes) {
    }
    
    @java.lang.Override()
    public void resetNavigate(@org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.navigation.NavigationAction navigationAction) {
    }
}