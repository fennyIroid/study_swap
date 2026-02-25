package com.studyswap.mobile.app.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u000b\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\u0082\u0001\u0003\r\u000e\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "Navigate", "NavigateIntent", "NavigateIntentWithFinishAffinity", "NavigateMultiple", "NavigateWithOptions", "NavigateWithPopUpTo", "Pop", "PopAndNavigate", "PopAndNavigateIntent", "PopIntent", "PopWithResult", "Lcom/studyswap/mobile/app/navigation/NavigationActionFull;", "Lcom/studyswap/mobile/app/navigation/NavigationActionIntent;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "app_debug"})
public abstract interface NavigationAction {
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0006\u001a\u00020\u0003H\u00c2\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J$\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016H\u0016J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0005\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001b"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$Navigate;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "(Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "component1", "component1-S7AQQSg", "()Ljava/lang/String;", "copy", "copy-COaT4hw", "(Ljava/lang/String;)Lcom/studyswap/mobile/app/navigation/NavigationAction$Navigate;", "equals", "", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class Navigate implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        
        private Navigate(java.lang.String route) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\t\u001a\u00020\u0003H\u00c2\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u00c2\u0003J\t\u0010\u000b\u001a\u00020\u0007H\u00c2\u0003J)\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J$\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016H\u0016J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateIntent;", "Lcom/studyswap/mobile/app/navigation/NavigationActionIntent;", "intent", "Landroid/content/Intent;", "options", "Landroid/os/Bundle;", "finishCurrentActivity", "", "(Landroid/content/Intent;Landroid/os/Bundle;Z)V", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "navigate", "context", "Landroid/content/Context;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class NavigateIntent implements com.studyswap.mobile.app.navigation.NavigationActionIntent {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Intent intent = null;
        @org.jetbrains.annotations.Nullable()
        private final android.os.Bundle options = null;
        private final boolean finishCurrentActivity = false;
        
        public NavigateIntent(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent, @org.jetbrains.annotations.Nullable()
        android.os.Bundle options, boolean finishCurrentActivity) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        private final android.content.Intent component1() {
            return null;
        }
        
        private final android.os.Bundle component2() {
            return null;
        }
        
        private final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.studyswap.mobile.app.navigation.NavigationAction.NavigateIntent copy(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent, @org.jetbrains.annotations.Nullable()
        android.os.Bundle options, boolean finishCurrentActivity) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0005\u001a\u00020\u0003H\u00c2\u0003J\u0013\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u00d6\u0003J\t\u0010\u000b\u001a\u00020\fH\u00d6\u0001J$\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011H\u0016J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateIntentWithFinishAffinity;", "Lcom/studyswap/mobile/app/navigation/NavigationActionIntent;", "intent", "Landroid/content/Intent;", "(Landroid/content/Intent;)V", "component1", "copy", "equals", "", "other", "", "hashCode", "", "navigate", "context", "Landroid/content/Context;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class NavigateIntentWithFinishAffinity implements com.studyswap.mobile.app.navigation.NavigationActionIntent {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Intent intent = null;
        
        public NavigateIntentWithFinishAffinity(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        private final android.content.Intent component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.studyswap.mobile.app.navigation.NavigationAction.NavigateIntentWithFinishAffinity copy(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J$\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014H\u0016J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0019"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateMultiple;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "routes", "", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "(Ljava/util/List;)V", "getRoutes", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class NavigateMultiple implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes = null;
        
        public NavigateMultiple(@org.jetbrains.annotations.NotNull()
        java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.studyswap.mobile.app.navigation.NavRoute> getRoutes() {
            return null;
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.studyswap.mobile.app.navigation.NavRoute> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.studyswap.mobile.app.navigation.NavigationAction.NavigateMultiple copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.studyswap.mobile.app.navigation.NavRoute> routes) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\f\u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\nJ\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J$\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001cH\u0016J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006!"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateWithOptions;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "navOptions", "Landroidx/navigation/NavOptions;", "(Ljava/lang/String;Landroidx/navigation/NavOptions;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getNavOptions", "()Landroidx/navigation/NavOptions;", "getRoute-S7AQQSg", "()Ljava/lang/String;", "Ljava/lang/String;", "component1", "component1-S7AQQSg", "component2", "copy", "copy-9zFSldk", "(Ljava/lang/String;Landroidx/navigation/NavOptions;)Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateWithOptions;", "equals", "", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class NavigateWithOptions implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        @org.jetbrains.annotations.NotNull()
        private final androidx.navigation.NavOptions navOptions = null;
        
        private NavigateWithOptions(java.lang.String route, androidx.navigation.NavOptions navOptions) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.navigation.NavOptions getNavOptions() {
            return null;
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.navigation.NavOptions component2() {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\n\u001a\u00020\u0003H\u00c2\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0016\u0010\r\u001a\u00020\u0005H\u00c2\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\fJ\t\u0010\u000f\u001a\u00020\u0007H\u00c2\u0003J1\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J$\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001cH\u0016J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\tR\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006!"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateWithPopUpTo;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "popUpToRouteDefinition", "Lcom/studyswap/mobile/app/navigation/NavRouteDefinition;", "inclusive", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "component1", "component1-S7AQQSg", "()Ljava/lang/String;", "component2", "component2-uKl5wyA", "component3", "copy", "copy-3_iWew8", "(Ljava/lang/String;Ljava/lang/String;Z)Lcom/studyswap/mobile/app/navigation/NavigationAction$NavigateWithPopUpTo;", "equals", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class NavigateWithPopUpTo implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String popUpToRouteDefinition = null;
        private final boolean inclusive = false;
        
        private NavigateWithPopUpTo(java.lang.String route, java.lang.String popUpToRouteDefinition, boolean inclusive) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        private final boolean component3() {
            return false;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u001b\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\b\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\t\u0010\nJ\t\u0010\u000b\u001a\u00020\u0005H\u00c2\u0003J)\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J$\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0018H\u0016J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001d"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$Pop;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "popToRouteDefinition", "Lcom/studyswap/mobile/app/navigation/NavRouteDefinition;", "inclusive", "", "(Ljava/lang/String;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "component1", "component1-x7p9XDI", "()Ljava/lang/String;", "component2", "copy", "copy-EcEeuvA", "(Ljava/lang/String;Z)Lcom/studyswap/mobile/app/navigation/NavigationAction$Pop;", "equals", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class Pop implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String popToRouteDefinition = null;
        private final boolean inclusive = false;
        
        private Pop(java.lang.String popToRouteDefinition, boolean inclusive) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        private final boolean component2() {
            return false;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0006\u001a\u00020\u0003H\u00c2\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J$\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016H\u0016J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\u0005\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001b"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$PopAndNavigate;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "route", "Lcom/studyswap/mobile/app/navigation/NavRoute;", "(Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "component1", "component1-S7AQQSg", "()Ljava/lang/String;", "copy", "copy-COaT4hw", "(Ljava/lang/String;)Lcom/studyswap/mobile/app/navigation/NavigationAction$PopAndNavigate;", "equals", "", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class PopAndNavigate implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String route = null;
        
        private PopAndNavigate(java.lang.String route) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u0007\u001a\u00020\u0003H\u00c2\u0003J\u000b\u0010\b\u001a\u0004\u0018\u00010\u0005H\u00c2\u0003J\u001f\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J,\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016H\u0016J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$PopAndNavigateIntent;", "Lcom/studyswap/mobile/app/navigation/NavigationActionFull;", "intent", "Landroid/content/Intent;", "options", "Landroid/os/Bundle;", "(Landroid/content/Intent;Landroid/os/Bundle;)V", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "navigate", "context", "Landroid/content/Context;", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class PopAndNavigateIntent implements com.studyswap.mobile.app.navigation.NavigationActionFull {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Intent intent = null;
        @org.jetbrains.annotations.Nullable()
        private final android.os.Bundle options = null;
        
        public PopAndNavigateIntent(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent, @org.jetbrains.annotations.Nullable()
        android.os.Bundle options) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        private final android.content.Intent component1() {
            return null;
        }
        
        private final android.os.Bundle component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.studyswap.mobile.app.navigation.NavigationAction.PopAndNavigateIntent copy(@org.jetbrains.annotations.NotNull()
        android.content.Intent intent, @org.jetbrains.annotations.Nullable()
        android.os.Bundle options) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c7\n\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00d6\u0003J\t\u0010\u0007\u001a\u00020\bH\u00d6\u0001J$\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0016J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001\u00a8\u0006\u0012"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$PopIntent;", "Lcom/studyswap/mobile/app/navigation/NavigationActionIntent;", "()V", "equals", "", "other", "", "hashCode", "", "navigate", "context", "Landroid/content/Context;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class PopIntent implements com.studyswap.mobile.app.navigation.NavigationActionIntent {
        @org.jetbrains.annotations.NotNull()
        public static final com.studyswap.mobile.app.navigation.NavigationAction.PopIntent INSTANCE = null;
        
        private PopIntent() {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c2\u0003J\u0018\u0010\f\u001a\u0004\u0018\u00010\u0006H\u00c2\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000eJ\t\u0010\u000f\u001a\u00020\bH\u00c2\u0003J9\u0010\u0010\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J$\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001cH\u0016J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\nR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006!"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavigationAction$PopWithResult;", "Lcom/studyswap/mobile/app/navigation/NavigationActionRoute;", "resultValues", "", "Lcom/studyswap/mobile/app/navigation/PopResultKeyValue;", "popToRouteDefinition", "Lcom/studyswap/mobile/app/navigation/NavRouteDefinition;", "inclusive", "", "(Ljava/util/List;Ljava/lang/String;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V", "Ljava/lang/String;", "component1", "component2", "component2-x7p9XDI", "()Ljava/lang/String;", "component3", "copy", "copy-xTfWKlU", "(Ljava/util/List;Ljava/lang/String;Z)Lcom/studyswap/mobile/app/navigation/NavigationAction$PopWithResult;", "equals", "other", "", "hashCode", "", "navigate", "navController", "Landroidx/navigation/NavController;", "resetNavigate", "Lkotlin/Function1;", "Lcom/studyswap/mobile/app/navigation/NavigationAction;", "", "toString", "", "app_debug"})
    public static final class PopWithResult implements com.studyswap.mobile.app.navigation.NavigationActionRoute {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.studyswap.mobile.app.navigation.PopResultKeyValue> resultValues = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String popToRouteDefinition = null;
        private final boolean inclusive = false;
        
        private PopWithResult(java.util.List<com.studyswap.mobile.app.navigation.PopResultKeyValue> resultValues, java.lang.String popToRouteDefinition, boolean inclusive) {
            super();
        }
        
        @java.lang.Override()
        public boolean navigate(@org.jetbrains.annotations.NotNull()
        androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.studyswap.mobile.app.navigation.NavigationAction, kotlin.Unit> resetNavigate) {
            return false;
        }
        
        private final java.util.List<com.studyswap.mobile.app.navigation.PopResultKeyValue> component1() {
            return null;
        }
        
        private final boolean component3() {
            return false;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}