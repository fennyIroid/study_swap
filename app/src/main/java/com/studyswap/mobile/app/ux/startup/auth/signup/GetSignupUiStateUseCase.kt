package com.studyswap.mobile.app.ux.startup.auth.signup

import android.content.Context
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.navigation.NavRoute
import com.studyswap.mobile.app.ux.startup.auth.LoginRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetSignupUiStateUseCase @Inject constructor() {

    private val _signupUiStateFlow = MutableStateFlow(SignupUiDataState())

    operator fun invoke(
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): SignupUiState = SignupUiState(
        signupUiStateFlow = _signupUiStateFlow,
        event = { event -> handleEvent(context, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        event: SignupUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is SignupUiEvent.OnFullNameChange -> _signupUiStateFlow.update {
                it.copy(fullName = event.value, fullNameError = null)
            }
            is SignupUiEvent.OnUniversityChange -> _signupUiStateFlow.update {
                it.copy(university = event.value, universityError = null)
            }
            is SignupUiEvent.OnEmailChange -> _signupUiStateFlow.update {
                it.copy(email = event.value.replace(" ", ""), emailError = null)
            }
            is SignupUiEvent.OnPasswordChange -> _signupUiStateFlow.update {
                it.copy(password = event.value, passwordError = null)
            }
            is SignupUiEvent.OnConfirmPasswordChange -> _signupUiStateFlow.update {
                it.copy(confirmPassword = event.value, confirmPasswordError = null)
            }
            is SignupUiEvent.OnTogglePassword -> _signupUiStateFlow.update {
                it.copy(isPasswordVisible = !it.isPasswordVisible)
            }
            is SignupUiEvent.OnToggleConfirmPassword -> _signupUiStateFlow.update {
                it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible)
            }
            is SignupUiEvent.OnCreateAccountClick -> {
                val state = _signupUiStateFlow.value
                val fullNameError = if (state.fullName.isBlank()) "Required" else null
                val universityError = if (state.university.isBlank()) "Required" else null
                val emailError = when {
                    state.email.isBlank() -> "Required"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches() -> "Invalid email"
                    else -> null
                }
                val passwordError = when {
                    state.password.isBlank() -> "Required"
                    state.password.length < 6 -> "At least 6 characters"
                    else -> null
                }
                val confirmError = if (state.confirmPassword != state.password) "Passwords must match" else null
                _signupUiStateFlow.update {
                    it.copy(
                        fullNameError = fullNameError,
                        universityError = universityError,
                        emailError = emailError,
                        passwordError = passwordError,
                        confirmPasswordError = confirmError
                    )
                }
                if (fullNameError == null && universityError == null && emailError == null && passwordError == null && confirmError == null) {
                    coroutineScope.launch {
                        _signupUiStateFlow.update { it.copy(isLoading = true) }
                        // TODO: call API when ready
                        _signupUiStateFlow.update { it.copy(isLoading = false) }
                    }
                }
            }
            is SignupUiEvent.OnLoginClick -> navigate(NavigationAction.Navigate(LoginRoute.createRoute()))
            is SignupUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
        }
    }
}
