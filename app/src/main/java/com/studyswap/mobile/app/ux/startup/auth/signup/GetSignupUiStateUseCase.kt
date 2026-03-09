package com.studyswap.mobile.app.ux.startup.auth.signup

import android.content.Context
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.navigation.NavRoute
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.SignupRequest
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.ux.startup.auth.login.LoginRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetSignupUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

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
            is SignupUiEvent.OnPhoneChange -> _signupUiStateFlow.update {
                it.copy(phone = event.value, phoneError = null)
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
                val phoneError = if (state.phone.isBlank()) "Required" else null
                val confirmError = if (state.confirmPassword != state.password) "Passwords must match" else null
                _signupUiStateFlow.update {
                    it.copy(
                        fullNameError = fullNameError,
                        universityError = universityError,
                        emailError = emailError,
                        passwordError = passwordError,
                        phoneError = phoneError,
                        confirmPasswordError = confirmError
                    )
                }
                if (fullNameError == null && universityError == null && emailError == null && passwordError == null && confirmError == null && phoneError == null) {
                    coroutineScope.launch {
                        val request = SignupRequest(
                            fullName = state.fullName,
                            email = state.email,
                            password = state.password,
                            phone = state.phone,
                            universityId = 1 // Default
                        )
                        apiRepository.signup(request).collect { result ->
                            when (result) {
                                is NetworkResult.Loading -> _signupUiStateFlow.update { it.copy(isLoading = true) }
                                is NetworkResult.Success -> {
                                    _signupUiStateFlow.update { it.copy(isLoading = false) }
                                    // Navigate to home or login after success
                                    navigate(NavigationAction.Navigate(LoginRoute.createRoute()))
                                }
                                is NetworkResult.Error -> {
                                    _signupUiStateFlow.update { it.copy(isLoading = false) }
                                    // Handle generic error showing if needed
                                }
                                is NetworkResult.UnAuthenticated -> {
                                    _signupUiStateFlow.update { it.copy(isLoading = false) }
                                }
                            }
                        }
                    }
                }
            }
            is SignupUiEvent.OnLoginClick -> navigate(NavigationAction.Navigate(LoginRoute.createRoute()))
            is SignupUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
        }
    }
}
