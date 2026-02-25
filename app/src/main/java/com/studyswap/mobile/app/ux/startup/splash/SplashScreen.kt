package com.studyswap.mobile.app.ux.startup.splash

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.studyswap.mobile.app.utils.ext.requireActivity
import com.studyswap.mobile.app.ui.theme.SplashBackground
import com.studyswap.mobile.app.ui.theme.SplashCardPeach
import com.studyswap.mobile.app.ui.theme.SplashCardSage
import com.studyswap.mobile.app.ui.theme.SplashLoadingText
import com.studyswap.mobile.app.ui.theme.SplashStudyText
import com.studyswap.mobile.app.ui.theme.SplashSwapText
import com.studyswap.mobile.app.ui.theme.SplashSwapUnderline
import com.studyswap.mobile.app.ui.theme.SplashTagline
import com.studyswap.mobile.app.ui.theme.SplashIconOnCard
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(LocalContext.current.requireActivity())
) {
    val nextRoute = viewModel.nextRoute.value

    LaunchedEffect(nextRoute) {
        if (nextRoute == null) return@LaunchedEffect
        delay(2500)
        navController.navigate(nextRoute) {
            popUpTo(SplashRoute.routeDefinition.value) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Decorative blur shapes (depth)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = (-24).dp),
                contentAlignment = Alignment.Center
            ) {
                val density = LocalDensity.current
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .offset(40.dp, (-20).dp)
                        .blur(24.dp)
                        .background(
                            SplashCardPeach.copy(alpha = 0.35f),
                            RoundedCornerShape(16.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .offset((-36).dp, 32.dp)
                        .blur(20.dp)
                        .background(
                            SplashCardSage.copy(alpha = 0.3f),
                            RoundedCornerShape(12.dp)
                        )
                )

                // Overlapping icon cards
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Sage green card (book)
                    Surface(
                        modifier = Modifier.size(72.dp).offset(x = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = SplashCardSage,
                        shadowElevation = 8.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MenuBook,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = SplashIconOnCard
                            )
                        }
                    }
                    // Peach card (swap)
                    Surface(
                        modifier = Modifier.size(72.dp).offset(x = (-8).dp),
                        shape = RoundedCornerShape(16.dp),
                        color = SplashCardPeach,
                        shadowElevation = 8.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SwapHoriz,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = SplashIconOnCard
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // "Study" + "Swap" with peach underline under "Swap" only
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Study",
                    fontSize = 32.sp,
                    color = SplashStudyText,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.5).sp
                )
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Swap",
                        fontSize = 32.sp,
                        color = SplashSwapText,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.5).sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(SplashSwapUnderline, RoundedCornerShape(1.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline
            Text(
                text = "Connecting students,\nexchanging knowledge.",
                fontSize = 15.sp,
                color = SplashTagline,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Loading indicator
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LoadingDots()
                Text(
                    text = "LOADING MATERIALS",
                    fontSize = 12.sp,
                    color = SplashLoadingText,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = tween(150)
        ),
        label = "dot2"
    )
    val alpha3 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = tween(300)
        ),
        label = "dot3"
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(SplashLoadingText.copy(alpha = alpha1), RoundedCornerShape(3.dp))
        )
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(SplashLoadingText.copy(alpha = alpha2), RoundedCornerShape(3.dp))
        )
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(SplashLoadingText.copy(alpha = alpha3), RoundedCornerShape(3.dp))
        )
    }
}
