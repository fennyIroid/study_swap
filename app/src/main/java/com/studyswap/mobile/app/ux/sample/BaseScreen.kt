package com.studyswap.mobile.app.ux.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Sample template: Empty screen composable.
 * Copy and rename (e.g. HomeScreen), collect state and call uiState.event(HomeUiEvent.OnXxx).
 */
@Composable
fun BaseScreen(
    viewModel: BaseViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val state by uiState.baseUiStateFlow.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Base screen template")
    }
}
