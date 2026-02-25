package com.studyswap.mobile.app.ux.startup.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.studyswap.mobile.app.ux.sample.BaseRoute
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate(BaseRoute.routeDefinition.value) {
            popUpTo(SplashRoute.routeDefinition.value) { inclusive = true }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Study Swap")
    }
}
