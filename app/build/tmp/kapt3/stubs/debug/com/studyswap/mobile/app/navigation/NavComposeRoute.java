package com.studyswap.mobile.app.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u00da\u0001\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2(\b\u0002\u0010\u000b\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\f\u00a2\u0006\u0002\b\u0010\u00a2\u0006\u0002\b\u00112(\b\u0002\u0010\u0012\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0018\u00010\f\u00a2\u0006\u0002\b\u0010\u00a2\u0006\u0002\b\u00112(\b\u0002\u0010\u0014\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\f\u00a2\u0006\u0002\b\u0010\u00a2\u0006\u0002\b\u00112(\b\u0002\u0010\u0015\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0018\u00010\f\u00a2\u0006\u0002\b\u0010\u00a2\u0006\u0002\b\u00112\"\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\b0\u0017\u00a2\u0006\u0002\b\u0019\u00a2\u0006\u0002\b\u0011J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH&J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001bH\u0016R\u0018\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u0006\u001f"}, d2 = {"Lcom/studyswap/mobile/app/navigation/NavComposeRoute;", "", "()V", "routeDefinition", "Lcom/studyswap/mobile/app/navigation/NavRouteDefinition;", "getRouteDefinition-uKl5wyA", "()Ljava/lang/String;", "addNavigationRoute", "", "navGraphBuilder", "Landroidx/navigation/NavGraphBuilder;", "enterTransition", "Lkotlin/Function1;", "Landroidx/compose/animation/AnimatedContentTransitionScope;", "Landroidx/navigation/NavBackStackEntry;", "Landroidx/compose/animation/EnterTransition;", "Lkotlin/jvm/JvmSuppressWildcards;", "Lkotlin/ExtensionFunctionType;", "exitTransition", "Landroidx/compose/animation/ExitTransition;", "popEnterTransition", "popExitTransition", "content", "Lkotlin/Function2;", "Landroidx/compose/animation/AnimatedContentScope;", "Landroidx/compose/runtime/Composable;", "getArguments", "", "Landroidx/navigation/NamedNavArgument;", "getDeepLinks", "Landroidx/navigation/NavDeepLink;", "app_debug"})
public abstract class NavComposeRoute {
    
    public NavComposeRoute() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<androidx.navigation.NamedNavArgument> getArguments();
    
    @org.jetbrains.annotations.NotNull()
    public java.util.List<androidx.navigation.NavDeepLink> getDeepLinks() {
        return null;
    }
    
    public final void addNavigationRoute(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavGraphBuilder navGraphBuilder, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<androidx.compose.animation.AnimatedContentTransitionScope<androidx.navigation.NavBackStackEntry>, androidx.compose.animation.EnterTransition> enterTransition, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<androidx.compose.animation.AnimatedContentTransitionScope<androidx.navigation.NavBackStackEntry>, androidx.compose.animation.ExitTransition> exitTransition, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<androidx.compose.animation.AnimatedContentTransitionScope<androidx.navigation.NavBackStackEntry>, androidx.compose.animation.EnterTransition> popEnterTransition, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<androidx.compose.animation.AnimatedContentTransitionScope<androidx.navigation.NavBackStackEntry>, androidx.compose.animation.ExitTransition> popExitTransition, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super androidx.compose.animation.AnimatedContentScope, ? super androidx.navigation.NavBackStackEntry, kotlin.Unit> content) {
    }
}