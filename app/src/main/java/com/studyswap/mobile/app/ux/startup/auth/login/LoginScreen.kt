package com.studyswap.mobile.app.ux.startup.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.studyswap.ui.theme.*
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.compose.common.StudySwapLogo
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray
import com.studyswap.mobile.app.ux.startup.auth.signup.SignupInputField
import com.studyswap.mobile.app.ux.startup.auth.signup.SignupPasswordField
import com.studyswap.mobile.app.ux.startup.auth.signup.SocialLoginButton

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)
    
    val uiStateData by viewModel.uiState.loginUiStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    LoginScreenContent(uiStateData = uiStateData, event = event)
}

@Composable
private fun LoginScreenContent(
    uiStateData: LoginUiDataState,
    event: (LoginUiEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
            .statusBarsPadding()
    ) {
        // 0. Back Button (Pinned)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp)
                .zIndex(1f), // Ensure it's on top
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = { event(LoginUiEvent.OnBackClick) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = TextCharcoal
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp)) // Increased spacer to account for back button area

            Spacer(modifier = Modifier.height(20.dp))

            // 1. Logo
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFFDF8F0), shape = RoundedCornerShape(100)),
                contentAlignment = Alignment.Center
            ) {
                 StudySwapLogo(
                    modifier = Modifier, size = 100.dp 
                )
            }
           
            Spacer(modifier = Modifier.height(32.dp))

            // 2. Header
            Text(
                text = "Let's get learning.",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your credentials to access your notes and connect with peers.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextMutedGray
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 3. Form
            SignupInputField(
                label = "Email Address",
                placeholder = "student@university.edu",
                icon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email,
                value = uiStateData.email,
                onValueChange = { event(LoginUiEvent.OnEmailChange(it)) },
                errorMessage = uiStateData.emailError
            )

            SignupPasswordField(
                label = "Password",
                placeholder = "••••••••",
                value = uiStateData.password,
                onValueChange = { event(LoginUiEvent.OnPasswordChange(it)) },
                isVisible = uiStateData.isPasswordVisible,
                onToggleVisibility = { event(LoginUiEvent.OnTogglePassword) },
                errorMessage = uiStateData.passwordError
            )

            // Forgot Password (aligned right)
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = PrimaryOlive,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Login Button
            Button(
                onClick = { event(LoginUiEvent.OnLoginClick) },
                enabled = !uiStateData.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOlive,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (uiStateData.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                Text(
                    text = "Or continue with",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMutedGray),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 6. Social Buttons (Google Only)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SocialLoginButton(
                    text = "Google",
                    modifier = Modifier.weight(1f)
                )

            }

            Spacer(modifier = Modifier.height(64.dp))

            // 7. Footer
            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New to StudySwap? ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedGray)
                )
                Text(
                    text = "Create an Account",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = PrimaryOlive,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable { event(LoginUiEvent.OnSignupClick) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    StudySwapTheme {
        LoginScreenContent(
            uiStateData = LoginUiDataState(),
            event = {}
        )
    }
}
