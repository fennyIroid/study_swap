package com.studyswap.mobile.app.ux.startup.auth.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginUiState(
    val loginUiStateFlow: StateFlow<LoginUiDataState> = MutableStateFlow(LoginUiDataState()),
    val event: (LoginUiEvent) -> Unit = {}
)

data class LoginUiDataState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface LoginUiEvent {
    data class OnEmailChange(val value: String) : LoginUiEvent
    data class OnPasswordChange(val value: String) : LoginUiEvent
    data object OnTogglePassword : LoginUiEvent
    data object OnLoginClick : LoginUiEvent
    data object OnSignupClick : LoginUiEvent
    data object OnBackClick : LoginUiEvent
}
