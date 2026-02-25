package com.studyswap.mobile.app.ux.startup.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.SplashCardSage
import com.studyswap.mobile.app.ui.theme.SplashStudyText
import com.studyswap.mobile.app.ui.theme.SplashSwapText
import com.studyswap.mobile.app.ui.theme.SignupDivider
import com.studyswap.mobile.app.ui.theme.SignupFieldBorder
import com.studyswap.mobile.app.ui.theme.SignupIconTint
import com.studyswap.mobile.app.ui.theme.SignupInfoBanner
import com.studyswap.mobile.app.ui.theme.WelcomeBackground

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    SignupScreenContent(navController = navController, uiState = uiState)
    HandleNavigation(viewModelNav = viewModel, navController = navController)
}

@Composable
private fun SignupScreenContent(
    navController: NavController,
    uiState: SignupUiState
) {
    val state by uiState.signupUiStateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WelcomeBackground)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        // Header: back + title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { uiState.event(SignupUiEvent.OnBackClick) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = SplashStudyText
                )
            }
            Text(
                text = "Sign Up",
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = SplashStudyText
            )
            Spacer(modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Icon with gradient (light green to peach)
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            SplashCardSage.copy(alpha = 0.4f),
                            Color(0xFFE8C4A8).copy(alpha = 0.6f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = SplashCardSage
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Start Learning Together",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SplashStudyText
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Create your account to access notes and study groups.",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 14.sp,
            color = SignupIconTint
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Full Name
        SignupLabel("Full Name")
        Spacer(modifier = Modifier.height(4.dp))
        SignupTextField(
            value = state.fullName,
            onValueChange = { uiState.event(SignupUiEvent.OnFullNameChange(it)) },
            placeholder = "Jane Doe",
            leadingIcon = Icons.Filled.Person,
            error = state.fullNameError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // University
        SignupLabel("University")
        Spacer(modifier = Modifier.height(4.dp))
        SignupTextField(
            value = state.university,
            onValueChange = { uiState.event(SignupUiEvent.OnUniversityChange(it)) },
            placeholder = "Search your university",
            leadingIcon = Icons.Filled.School,
            error = state.universityError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        SignupLabel("Email Address")
        Spacer(modifier = Modifier.height(4.dp))
        SignupTextField(
            value = state.email,
            onValueChange = { uiState.event(SignupUiEvent.OnEmailChange(it)) },
            placeholder = "jane@student.edu",
            leadingIcon = Icons.Filled.Email,
            error = state.emailError,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password
        SignupLabel("Password")
        Spacer(modifier = Modifier.height(4.dp))
        SignupTextField(
            value = state.password,
            onValueChange = { uiState.event(SignupUiEvent.OnPasswordChange(it)) },
            placeholder = "********",
            leadingIcon = Icons.Filled.Lock,
            trailingIcon = if (state.isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            onTrailingIconClick = { uiState.event(SignupUiEvent.OnTogglePassword) },
            error = state.passwordError,
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password
        SignupLabel("Confirm Password")
        Spacer(modifier = Modifier.height(4.dp))
        SignupTextField(
            value = state.confirmPassword,
            onValueChange = { uiState.event(SignupUiEvent.OnConfirmPasswordChange(it)) },
            placeholder = "********",
            leadingIcon = Icons.Filled.Lock,
            trailingIcon = if (state.isConfirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            onTrailingIconClick = { uiState.event(SignupUiEvent.OnToggleConfirmPassword) },
            error = state.confirmPasswordError,
            visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Info banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SignupInfoBanner)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = SplashCardSage
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "You can complete your academic profile after signup.",
                fontSize = 13.sp,
                color = SplashStudyText
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Create Account button
        Button(
            onClick = { uiState.event(SignupUiEvent.OnCreateAccountClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !state.isLoading,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SplashCardSage)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Create Account", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Or continue with
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f).height(1.dp).background(SignupDivider))
            Text(
                text = " Or continue with ",
                fontSize = 13.sp,
                color = SignupIconTint,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Box(modifier = Modifier.weight(1f).height(1.dp).background(SignupDivider))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Google only (no Apple)
        OutlinedButton(
            onClick = { /* TODO: Google sign-in */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = SplashStudyText
            )
        ) {
            Text("Google", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Already a member? Login
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Already a member? ")
                    withStyle(SpanStyle(color = SplashCardSage, fontWeight = FontWeight.SemiBold)) {
                        append("Login")
                    }
                },
                fontSize = 14.sp,
                color = SplashStudyText,
                modifier = Modifier.clickable { uiState.event(SignupUiEvent.OnLoginClick) }
            )
        }
    }
}

@Composable
private fun SignupLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = SplashStudyText
    )
}

@Composable
private fun SignupTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = SignupIconTint, fontSize = 14.sp) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = SignupIconTint
            )
        },
        trailingIcon = if (trailingIcon != null && onTrailingIconClick != null) {
            {
                IconButton(onClick = onTrailingIconClick) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = if (value.isEmpty()) null else "Toggle visibility",
                        modifier = Modifier.size(22.dp),
                        tint = SignupIconTint
                    )
                }
            }
        } else null,
        isError = error != null,
        supportingText = if (error != null) { { Text(error, color = Color(0xFFB00020), fontSize = 12.sp) } } else null,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SplashCardSage,
            unfocusedBorderColor = SignupFieldBorder,
            errorBorderColor = Color(0xFFB00020),
            focusedTextColor = SplashStudyText,
            unfocusedTextColor = SplashStudyText,
            cursorColor = SplashCardSage,
            focusedLeadingIconColor = SignupIconTint,
            unfocusedLeadingIconColor = SignupIconTint
        ),
        modifier = modifier
    )
}
