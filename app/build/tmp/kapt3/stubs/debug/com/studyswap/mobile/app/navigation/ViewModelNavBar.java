package com.studyswap.mobile.app.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003J\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001c\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017H&J\"\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001bJ3\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0017\u0010\u001c\u001a\u0013\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\f0\u001d\u00a2\u0006\u0002\b\u001fH&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010 J$\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010!\u001a\u00020\u0010H&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000e0#H&J&\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00028\u00002\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH&\u00f8\u0001\u0000\u00a2\u0006\u0004\b&\u0010\'J\u0010\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\u0006H&R\u001a\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\b\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "T", "", "", "navigatorFlow", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "getNavigatorFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedNavBarFlow", "getSelectedNavBarFlow", "navBarNavigation", "", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "reselected", "", "navBarNavigation-9zFSldk", "(Ljava/lang/String;Z)V", "navigate", "intent", "Landroid/content/Intent;", "options", "Landroid/os/Bundle;", "navOptions", "Landroidx/navigation/NavOptions;", "navigate-9zFSldk", "(Ljava/lang/String;Landroidx/navigation/NavOptions;)V", "optionsBuilder", "Lkotlin/Function1;", "Landroidx/navigation/NavOptionsBuilder;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "popBackStack", "routes", "", "onNavBarItemSelected", "selectedItem", "onNavBarItemSelected-G-ImXsU", "(Ljava/lang/Enum;Ljava/lang/String;)V", "resetNavigate", "viewModelNavBarNavigator", "app_debug"})
public abstract interface ViewModelNavBar<T extends java.lang.Enum<T>> {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator> getNavigatorFlow();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<T> getSelectedNavBarFlow();
    
    public abstract void navigate(@org.jetbrains.annotations.NotNull()
    java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes);
    
    public abstract void navigate(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, @org.jetbrains.annotations.Nullable()
    android.os.Bundle options);
    
    public abstract void resetNavigate(@org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator viewModelNavBarNavigator);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}