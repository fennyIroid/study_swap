package com.example.studyswap.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import com.studyswap.mobile.app.ux.startup.splash.SplashViewModel
import com.studyswap.mobile.app.ux.startup.splash.SplashRoute
import com.example.studyswap.ui.theme.*
import com.studyswap.mobile.app.ui.theme.AccentCream
import com.studyswap.mobile.app.ui.theme.AccentMint
import com.studyswap.mobile.app.ui.theme.Dimens
import com.studyswap.mobile.app.ui.theme.Gradients
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray

@Composable
fun StudySwapLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        // Left card (Peach / Cream)
        Box(
            modifier = Modifier
                .offset(x = (-12).dp, y = 0.dp)
                .rotate(-12f)
                .size(width = 70.dp, height = 100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(SecondaryPeach, AccentCream),
                    )
                )
        )

        // Right card (Olive / Mint)
        Box(
            modifier = Modifier
                .offset(x = 12.dp, y = 0.dp)
                .rotate(12f)
                .size(width = 70.dp, height = 100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PrimaryOlive, AccentMint),
                    )
                )
        )

        // Center swap icon container
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "⇄",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = SecondaryPeach,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = (-2).dp)
            )
        }
    }
}

@Composable
fun SplashScreen(
    navController: NavController? = null,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val nextRoute by viewModel.nextRoute.collectAsStateWithLifecycle()

    LaunchedEffect(nextRoute) {
        nextRoute?.let { destination ->
            delay(2000)
            navController?.navigate(destination) {
                popUpTo(SplashRoute.routeDefinition.value) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gradients.Background)
    ) {
        // 🌟 CENTER CONTENT
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(Dimens.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // LOGO
            StudySwapLogo()

            Spacer(modifier = Modifier.height(32.dp))

            // App name: Study Swap
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TextCharcoal)) { // Updated
                        append("Study")
                    }
                    withStyle(style = SpanStyle(color = SecondaryPeach)) { // Updated
                        append(" Swap")
                    }
                },
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Connect. Learn. Exchange.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                ),
                color = TextMutedGray // Updated
            )
        }

        // ⏳ BOTTOM LOADING INDICATOR
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Custom Progress Bar
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)) // Light gray track
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f) // Static 50% for splash demo
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(PrimaryOlive, SecondaryPeach) // Updated
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "FINDING STUDY BUDDIES...",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = TextMutedGray // Updated
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun SplashScreenPreview() {
    StudySwapTheme {
        SplashScreen()
    }
}