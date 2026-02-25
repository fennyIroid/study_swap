package com.studyswap.mobile.app.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J&\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH&J\"\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014J3\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\b0\u0016\u00a2\u0006\u0002\b\u0018H&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0019J$\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\r\u001a\u00020\u000eH&\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u001aJ\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u0004H&J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00100\u001dH&J(\u0010\r\u001a\u00020\b2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\b\u0002\u0010 \u001a\u00020\u000eH&\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\u001aJ6\u0010\"\u001a\u00020\b2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\b\u0002\u0010 \u001a\u00020\u000eH&\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010&J\u0010\u0010\'\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u0004H&R\u001a\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006("}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNav;", "", "navigationActionFlow", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "getNavigationActionFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "navigate", "", "intent", "Landroid/content/Intent;", "options", "Landroid/os/Bundle;", "popBackStack", "", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "navOptions", "Landroidx/navigation/NavOptions;", "navigate-9zFSldk", "(Ljava/lang/String;Landroidx/navigation/NavOptions;)V", "optionsBuilder", "Lkotlin/Function1;", "Landroidx/navigation/NavOptionsBuilder;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "(Ljava/lang/String;Z)V", "navigationAction", "routes", "", "popToRouteDefinition", "Lcom/studyswap/mobile/app/navigation/NavRouteDefinition;", "inclusive", "popBackStack-EcEeuvA", "popBackStackWithResult", "resultValues", "Lcom/studyswap/mobile/app/navigation/PopResultKeyValue;", "popBackStackWithResult-xTfWKlU", "(Ljava/util/List;Ljava/lang/String;Z)V", "resetNavigate", "app_debug"})
public abstract interface ViewModelNav {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<com.studyswap.mobile.app.navigation.NavigationAction> getNavigationActionFlow();
    
    public abstract void navigate(@org.jetbrains.annotations.NotNull()
    java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes);
    
    public abstract void navigate(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, @org.jetbrains.annotations.Nullable()
    android.os.Bundle options, boolean popBackStack);
    
    public abstract void navigate(@org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.navigation.NavigationAction navigationAction);
    
    public abstract void resetNavigate(@org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.navigation.NavigationAction navigationAction);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}