package com.studyswap.mobile.app.ux.startup.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.studyswap.mobile.app.ux.startup.auth.signup.SignupRoute
import com.studyswap.mobile.app.ui.theme.SplashCardSage
import com.studyswap.mobile.app.ui.theme.SplashStudyText
import com.studyswap.mobile.app.ui.theme.SplashSwapText
import com.studyswap.mobile.app.ui.theme.WelcomeBackground
import com.studyswap.mobile.app.ui.theme.WelcomeButtonSecondaryBg
import com.studyswap.mobile.app.ui.theme.WelcomeCardBeige
import com.studyswap.mobile.app.ui.theme.WelcomeDisclaimer
import com.studyswap.mobile.app.ui.theme.WelcomeIllustrationBg

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(WelcomeBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Illustration card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(WelcomeCardBeige)
                    .padding(24.dp)
            ) {
                // Placeholder illustration area (students/study scene)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(WelcomeIllustrationBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = SplashCardSage.copy(alpha = 0.6f)
                    )
                    // Floating icons
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(WelcomeCardBeige)
                            .border(1.dp, Color(0xFF8B7355).copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.School,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF6B5344)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(WelcomeCardBeige)
                            .border(1.dp, Color(0xFF8B7355).copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChatBubbleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF6B5344)
                        )
                    }
                }
            }
        }

        // Content card (white, with drag handle)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp, bottom = 32.dp)
        ) {
            // Drag handle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFFE0E0E0))
                )
            }

            // StudySwap title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Study",
                    fontSize = 28.sp,
                    color = SplashStudyText,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Swap",
                    fontSize = 28.sp,
                    color = SplashSwapText,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline
            Text(
                text = "Your Campus Study Community.\nFind partners, trade books, and ace\nexams together.",
                fontSize = 15.sp,
                color = Color(0xFF6B6B6B),
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Sign Up button
            Button(
                onClick = { navController.navigate(SignupRoute.routeDefinition.value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SplashCardSage),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Text("Sign Up", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Log In button
            OutlinedButton(
                onClick = { navController.navigate(LoginRoute.routeDefinition.value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = WelcomeButtonSecondaryBg,
                    contentColor = SplashCardSage
                ),
                border = null
            ) {
                Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Disclaimer
            Text(
                text = "By continuing, you agree to our Terms & Privacy Policy",
                fontSize = 12.sp,
                color = WelcomeDisclaimer,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
