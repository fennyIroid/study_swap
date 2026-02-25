package com.studyswap.mobile.app.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0006\r\u000e\u000f\u0010\u0011\u0012B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u0004\"\u000e\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\fH&\u0082\u0001\u0006\u0013\u0014\u0015\u0016\u0017\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "", "()V", "navigate", "", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "NavBarNavigate", "Navigate", "NavigateIntent", "NavigateMultiple", "NavigateWithOptions", "PopAndNavigate", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavBarNavigate;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$Navigate;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavigateIntent;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavigateMultiple;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavigateWithOptions;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$PopAndNavigate;", "app_debug"})
public abstract class ViewModelNavBarNavigator {
    
    private ViewModelNavBarNavigator() {
        super();
    }
    
    public abstract <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J6\u0010\b\u001a\u00020\u0005\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\t0\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u0011"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavBarNavigate;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "reselected", "", "(Ljava/lang/String;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "navigate", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "app_debug"})
    public static final class NavBarNavigate extends com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        private final boolean reselected = false;
        
        private NavBarNavigate(java.lang.String route, boolean reselected) {
        }
        
        @java.lang.Override()
        public <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J6\u0010\u0006\u001a\u00020\u0007\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\b0\u000fH\u0016R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0005\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u0010"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$Navigate;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "(Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "navigate", "", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "app_debug"})
    public static final class Navigate extends com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        
        private Navigate(java.lang.String route) {
        }
        
        @java.lang.Override()
        public <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J6\u0010\u000b\u001a\u00020\f\"\u000e\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\r0\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0015"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavigateIntent;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "intent", "Landroid/content/Intent;", "options", "Landroid/os/Bundle;", "(Landroid/content/Intent;Landroid/os/Bundle;)V", "getIntent", "()Landroid/content/Intent;", "getOptions", "()Landroid/os/Bundle;", "navigate", "", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "app_debug"})
    public static final class NavigateIntent extends com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Intent intent = null;
        @org.jetbrains.annotations.Nullable()
        private final android.os.Bundle options = null;
        
        public NavigateIntent(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent, @org.jetbrains.annotations.Nullable()
        android.os.Bundle options) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent getIntent() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final android.os.Bundle getOptions() {
            return null;
        }
        
        @java.lang.Override()
        public <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J6\u0010\u0006\u001a\u00020\u0007\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\b0\u000fH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavigateMultiple;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "routes", "", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "(Ljava/util/List;)V", "navigate", "", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "app_debug"})
    public static final class NavigateMultiple extends com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes = null;
        
        public NavigateMultiple(@org.jetbrains.annotations.NotNull()
        java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes) {
        }
        
        @java.lang.Override()
        public <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J6\u0010\b\u001a\u00020\t\"\u000e\b\u0000\u0010\n*\b\u0012\u0004\u0012\u0002H\n0\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\n0\u0011H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u0012"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$NavigateWithOptions;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "navOptions", "Landroidx/navigation/NavOptions;", "(Ljava/lang/String;Landroidx/navigation/NavOptions;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "navigate", "", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "app_debug"})
    public static final class NavigateWithOptions extends com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.navigation.NavOptions navOptions = null;
        
        private NavigateWithOptions(java.lang.String route, androidx.navigation.NavOptions navOptions) {
        }
        
        @java.lang.Override()
        public <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J6\u0010\u0006\u001a\u00020\u0007\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\b0\u000fH\u0016R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0005\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u0010"}, d2 = {"Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator$PopAndNavigate;", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBarNavigator;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "(Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "navigate", "", "T", "", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "viewModelNav", "Lcom/studyswap/mobile/app/navigation/ViewModelNavBar;", "app_debug"})
    public static final class PopAndNavigate extends com.studyswap.mobile.app.navigation.ViewModelNavBarNavigator {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        
        private PopAndNavigate(java.lang.String route) {
        }
        
        @java.lang.Override()
        public <T extends java.lang.Enum<T>>boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        com.studyswap.mobile.app.navigation.ViewModelNavBar<T> viewModelNav) {
            return false;
        }
    }
}