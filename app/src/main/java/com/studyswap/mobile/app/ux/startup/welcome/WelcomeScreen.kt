package com.studyswap.mobile.app.ux.startup.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyswap.ui.theme.*
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.Gradients
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray

@Composable
fun WelcomeScreen(
    onSignupClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gradients.Background)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top illustration area – use a themed composable instead of a bitmap
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f)
                    .padding(horizontal = 32.dp, vertical = 24.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color(0xFFFDF1E6)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFF8DA082).copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "A",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryOlive
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFFEBC8A4)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "B",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFFE1F2E8)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "C",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryOlive
                            )
                        }
                    }

                    Text(
                        text = "Study together. Swap notes.\nGrow your GPA.",
                        textAlign = TextAlign.Center,
                        color = TextCharcoal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Bottom Sheet / Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 70.dp, topEnd = 32.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Drag Handle Indicator
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.LightGray.copy(alpha = 0.4f))
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Logo Text
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = TextCharcoal)) {
                            append("Study")
                        }
                        withStyle(style = SpanStyle(color = PrimaryOlive)) { // Design shows variations, sticking to primary theme or matching design
                            // Design shows "Swap" in a green tone. PrimaryOlive matches well.
                            append("Swap")
                        }
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tagline
                Text(
                    text = "Your Campus Study Community.\nFind partners, trade books, and ace\nexams together.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TextMutedGray,
                        lineHeight = 24.sp
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Sign Up Button
                Button(
                    onClick = onSignupClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryOlive,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    // Arrow Icon
                    Text(text = "→", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Log In Button
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BackgroundOffWhite, // Or White with Border
                        contentColor = PrimaryOlive
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)) // Subtle border
                ) {
                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Terms
                Text(
                    text = "By continuing, you agree to our Terms & Privacy Policy",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMutedGray.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun WelcomeScreenPreview() {
    StudySwapTheme {
        WelcomeScreen(onSignupClick = {}, onLoginClick = {})
    }
}
