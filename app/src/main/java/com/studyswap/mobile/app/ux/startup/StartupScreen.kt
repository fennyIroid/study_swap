package com.studyswap.mobile.app.ux.startup

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.navigation.graph.AppStartupGraph
import com.studyswap.mobile.app.utils.ext.requireActivity

@Composable
fun StartupScreen(
    viewModel: StartupViewModel = hiltViewModel(LocalContext.current.requireActivity()),
    startDestination: String = ""
) {
    val navController = rememberNavController()
    AppStartupGraph(navController = navController, startDestination = startDestination)
    HandleNavigation(viewModelNav = viewModel, navController = navController)
}
