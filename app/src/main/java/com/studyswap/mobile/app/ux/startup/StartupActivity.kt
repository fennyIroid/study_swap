package com.studyswap.mobile.app.ux.startup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySwapTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StartupScreen(startDestination = "")
                }
            }
        }
    }
}
