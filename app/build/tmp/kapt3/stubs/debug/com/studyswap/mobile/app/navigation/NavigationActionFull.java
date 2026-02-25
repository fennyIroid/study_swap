package com.studyswap.mobile.app.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\n0\tH&\u0082\u0001\u0001\u000b\u00a8\u0006\f"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationActionFull;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "navigate", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "", "Lcom/studyswap/mobile/app/navigation/NavigationAction$PopAndNavigateIntent;", "app_debug"})
public abstract interface NavigationActionFull extends com.studyswap.mobile.app.navigation.NavigationAction {
    
    public abstract boolean navigate(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate);
}