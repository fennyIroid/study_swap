package com.example.studyswap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StudySwapLogo(
    modifier: Modifier = Modifier,
    size: Dp = 160.dp // 👈 control overall size
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {

        val cardWidth = size * 0.44f
        val cardHeight = size * 0.62f
        val iconSize = size * 0.35f

        // Left card (Peach / Cream)
        Box(
            modifier = Modifier
                .offset(x = (-size * 0.075f))
                .rotate(-12f)
                .size(width = cardWidth, height = cardHeight)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            com.example.studyswap.ui.theme.SecondaryPeach,
                            com.example.studyswap.ui.theme.AccentCream
                        )
                    )
                )
        )

        // Right card (Olive / Mint)
        Box(
            modifier = Modifier
                .offset(x = (size * 0.075f))
                .rotate(12f)
                .size(width = cardWidth, height = cardHeight)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            com.example.studyswap.ui.theme.PrimaryOlive,
                            com.example.studyswap.ui.theme.AccentMint
                        )
                    )
                )
        )

        // Center swap icon
        Box(
            modifier = Modifier
                .size(iconSize)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "⇄",
                fontSize = (iconSize.value * 0.55f).sp,
                fontWeight = FontWeight.Bold,
                color = com.example.studyswap.ui.theme.SecondaryPeach, // Updated color
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = (-2).dp)
            )
        }
    }
}