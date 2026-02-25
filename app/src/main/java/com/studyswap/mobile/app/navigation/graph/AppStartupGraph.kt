package com.studyswap.mobile.app.navigation.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studyswap.ui.splash.SplashScreen
import com.example.studyswap.ui.welcome.WelcomeScreen
import com.studyswap.mobile.app.ux.sample.BaseRoute
import com.studyswap.mobile.app.ux.sample.BaseScreen
import com.studyswap.mobile.app.ux.startup.auth.LoginRoute
import com.studyswap.mobile.app.ux.startup.auth.LoginScreen
import com.studyswap.mobile.app.ux.startup.auth.signup.SignupRoute
import com.studyswap.mobile.app.ux.startup.auth.signup.SignupScreen
import com.studyswap.mobile.app.ux.startup.splash.SplashRoute
import com.studyswap.mobile.app.ux.startup.welcome.WelcomeRoute

@Composable
fun AppStartupGraph(
    navController: NavHostController,
    startDestination: String
) {
    val initialRoute = startDestination.ifEmpty { SplashRoute.routeDefinition.value }

    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(SplashRoute.routeDefinition.value) {
            SplashScreen(navController = navController)
        }
        composable(WelcomeRoute.routeDefinition.value) {
            WelcomeScreen(
                onSignupClick = {
                    navController.navigate(SignupRoute.routeDefinition.value)
                },
                onLoginClick = {
                    navController.navigate(LoginRoute.routeDefinition.value)
                }
            )
        }
        composable(SignupRoute.routeDefinition.value) {
            SignupScreen(navController = navController)
        }
        composable(LoginRoute.routeDefinition.value) {
            LoginScreen(navController = navController)
        }
        composable(BaseRoute.routeDefinition.value) {
            BaseScreen()
        }
    }
}
