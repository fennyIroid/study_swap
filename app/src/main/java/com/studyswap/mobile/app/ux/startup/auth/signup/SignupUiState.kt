package com.studyswap.mobile.app.ux.startup.auth.signup

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SignupUiState(
    val signupUiStateFlow: StateFlow<SignupUiDataState> = MutableStateFlow(SignupUiDataState()),
    val event: (SignupUiEvent) -> Unit = {}
)

data class SignupUiDataState(
    val fullName: String = "",
    val fullNameError: String? = null,
    val university: String = "",
    val universityError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val isConfirmPasswordVisible: Boolean = false,
    val phone: String = "",
    val phoneError: String? = null,
    val isLoading: Boolean = false
)

sealed interface SignupUiEvent {
    data class OnFullNameChange(val value: String) : SignupUiEvent
    data class OnPhoneChange(val value: String) : SignupUiEvent
    data class OnUniversityChange(val value: String) : SignupUiEvent
    data class OnEmailChange(val value: String) : SignupUiEvent
    data class OnPasswordChange(val value: String) : SignupUiEvent
    data class OnConfirmPasswordChange(val value: String) : SignupUiEvent
    data object OnTogglePassword : SignupUiEvent
    data object OnToggleConfirmPassword : SignupUiEvent
    data object OnCreateAccountClick : SignupUiEvent
    data object OnLoginClick : SignupUiEvent
    data object OnBackClick : SignupUiEvent
}
