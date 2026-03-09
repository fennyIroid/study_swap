package com.studyswap.mobile.app.ux.startup.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.studyswap.mobile.app.R
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.compose.common.StudySwapLogo
import com.studyswap.mobile.app.ui.theme.*

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)
    
    val uiStateData by viewModel.uiState.signupUiStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    SignupScreenContent(uiStateData = uiStateData, event = event)
}

@Composable
private fun SignupScreenContent(
    uiStateData: SignupUiDataState,
    event: (SignupUiEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
    ) {
        // 1. Top Bar (Pinned)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { event(SignupUiEvent.OnBackClick) },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = TextCharcoal
                )
            }
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .padding(top = 60.dp, start = 24.dp, end = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Logo / Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Gradients.PrimaryButton
                    ),
                contentAlignment = Alignment.Center
            ) {
                StudySwapLogo(
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Header Text
            Text(
                text = "Start Learning Together",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Create your account to access notes and study groups.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextMutedGray
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Form Fields
            SignupInputField(
                label = "Full Name",
                placeholder = "John Doe",
                icon = Icons.Outlined.Person,
                value = uiStateData.fullName,
                onValueChange = { event(SignupUiEvent.OnFullNameChange(it)) },
                errorMessage = uiStateData.fullNameError
            )

            SignupInputField(
                label = "Email Address",
                placeholder = "john@example.com",
                icon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email,
                value = uiStateData.email,
                onValueChange = { event(SignupUiEvent.OnEmailChange(it)) },
                errorMessage = uiStateData.emailError
            )

            SignupInputField(
                label = "Phone Number",
                placeholder = "9662154155",
                icon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone,
                value = uiStateData.phone,
                onValueChange = { event(SignupUiEvent.OnPhoneChange(it)) },
                errorMessage = uiStateData.phoneError
            )

            SignupInputField(
                label = "University Name",
                placeholder = "Harvard",
                icon = Icons.Outlined.Star,
                keyboardType = KeyboardType.Text,
                value = uiStateData.university,
                onValueChange = { event(SignupUiEvent.OnUniversityChange(it)) },
                errorMessage = uiStateData.universityError
            )

            SignupPasswordField(
                label = "Password",
                placeholder = "••••••••",
                value = uiStateData.password,
                onValueChange = { event(SignupUiEvent.OnPasswordChange(it)) },
                isVisible = uiStateData.isPasswordVisible,
                onToggleVisibility = { event(SignupUiEvent.OnTogglePassword) },
                errorMessage = uiStateData.passwordError
            )

            SignupPasswordField(
                label = "Confirm Password",
                placeholder = "••••••••",
                value = uiStateData.confirmPassword,
                onValueChange = { event(SignupUiEvent.OnConfirmPasswordChange(it)) },
                isVisible = uiStateData.isConfirmPasswordVisible,
                onToggleVisibility = { event(SignupUiEvent.OnToggleConfirmPassword) },
                errorMessage = uiStateData.confirmPasswordError
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 6. Create Account Button
            Button(
                onClick = { event(SignupUiEvent.OnCreateAccountClick) },
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
                        text = "Create Account",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 7. Divider
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

            Spacer(modifier = Modifier.height(24.dp))

            // 8. Social Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialLoginButton(
                    text = "Google",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 9. Footer
            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already a member? ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedGray)
                )
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = PrimaryOlive,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable { event(SignupUiEvent.OnLoginClick) }
                )
            }
        }
    }
}

@Composable
fun SignupInputField(
    label: String,
    placeholder: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String? = null
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (errorMessage != null) MaterialTheme.colorScheme.error else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SurfaceWhite,
                unfocusedContainerColor = SurfaceWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = PrimaryOlive,
                focusedTextColor = TextCharcoal,
                unfocusedTextColor = TextCharcoal
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextMutedGray.copy(alpha = 0.7f)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TextMutedGray.copy(alpha = 0.5f)
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = errorMessage != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun SignupPasswordField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    errorMessage: String? = null
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (errorMessage != null) MaterialTheme.colorScheme.error else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SurfaceWhite,
                unfocusedContainerColor = SurfaceWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = PrimaryOlive,
                focusedTextColor = TextCharcoal,
                unfocusedTextColor = TextCharcoal
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextMutedGray.copy(alpha = 0.7f)
                )
            },
            trailingIcon = {
                val text = if (isVisible) "Hide" else "Show"

                TextButton(onClick = onToggleVisibility) {
                    Text(
                        text = text,
                        color = TextMutedGray,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = errorMessage != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /* TODO */ },
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceWhite,
            contentColor = TextCharcoal
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    StudySwapTheme {
        SignupScreenContent(
            uiStateData = SignupUiDataState(),
            event = {}
        )
    }
}