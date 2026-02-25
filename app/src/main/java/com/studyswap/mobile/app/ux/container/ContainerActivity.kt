package com.studyswap.mobile.app.ux.container

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySwapTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ContainerPlaceholderScreen()
                }
            }
        }
    }
}

@Composable
private fun ContainerPlaceholderScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Container")
    }
}
