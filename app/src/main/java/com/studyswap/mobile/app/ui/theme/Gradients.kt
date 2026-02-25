package com.studyswap.mobile.app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Gradients {
    // Primary CTA buttons (Login / Signup)
    val PrimaryButton = Brush.horizontalGradient(
        colors = listOf(
            PrimaryOlive,
            SecondaryPeach
        )
    )

    // Text gradients (titles, links)
    val TextGradient = Brush.horizontalGradient(
        colors = listOf(
            PrimaryOlive,
            SecondaryPeach
        )
    )

    // Background gradient (soft pastel)
    val Background = Brush.verticalGradient(
        colors = listOf(
            GradientStart,
            GradientEnd
        )
    )
}