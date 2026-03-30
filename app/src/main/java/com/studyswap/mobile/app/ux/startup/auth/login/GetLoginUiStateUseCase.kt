package com.studyswap.mobile.app.ux.startup.auth.login

import android.content.Context
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.navigation.NavRoute
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.*
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.ux.startup.auth.signup.SignupRoute
import com.studyswap.mobile.app.ux.main.home.HomeRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetLoginUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataStore: AppPreferenceDataStore
) {

    private val _loginUiStateFlow = MutableStateFlow(LoginUiDataState())

    operator fun invoke(
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): LoginUiState = LoginUiState(
        loginUiStateFlow = _loginUiStateFlow,
        event = { event -> handleEvent(context, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        event: LoginUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is LoginUiEvent.OnEmailChange -> _loginUiStateFlow.update {
                it.copy(email = event.value.replace(" ", ""), emailError = null)
            }
            is LoginUiEvent.OnPasswordChange -> _loginUiStateFlow.update {
                it.copy(password = event.value, passwordError = null)
            }
            is LoginUiEvent.OnTogglePassword -> _loginUiStateFlow.update {
                it.copy(isPasswordVisible = !it.isPasswordVisible)
            }
            is LoginUiEvent.OnLoginClick -> {
                val state = _loginUiStateFlow.value
                val emailError = when {
                    state.email.isBlank() -> "Required"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches() -> "Invalid email"
                    else -> null
                }
                val passwordError = if (state.password.isBlank()) "Required" else null
                
                _loginUiStateFlow.update {
                    it.copy(
                        emailError = emailError,
                        passwordError = passwordError
                    )
                }
                
                if (emailError == null && passwordError == null) {
                    coroutineScope.launch {
                        val request = LoginRequest(
                            email = state.email,
                            password = state.password,
                            loginType = "email"
                        )
                        apiRepository.login(request).collect { result ->
                            when (result) {
                                is NetworkResult.Loading -> _loginUiStateFlow.update { it.copy(isLoading = true) }
                                is NetworkResult.Success -> {
                                    val loginData = result.data
                                    loginData?.token?.let { dataStore.saveUserToken(it) }

                                    coroutineScope.launch {
                                        apiRepository.getUserInfo().collect { infoResult ->
                                            if (infoResult is NetworkResult.Success) {
                                                infoResult.data?.userData?.let { dataStore.saveUserData(it) }
                                            }
                                        }
                                    }

                                    _loginUiStateFlow.update { it.copy(isLoading = false) }
                                    navigate(NavigationAction.Navigate(HomeRoute.createRoute()))
                                }
                                is NetworkResult.Error -> {
                                    _loginUiStateFlow.update { it.copy(isLoading = false) }
                                    // Handle generic error showing if needed
                                }
                                is NetworkResult.UnAuthenticated -> {
                                    _loginUiStateFlow.update { it.copy(isLoading = false) }
                                }
                            }
                        }
                    }
                }
            }
            is LoginUiEvent.OnSignupClick -> navigate(NavigationAction.Navigate(SignupRoute.createRoute()))
            is LoginUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
        }
    }
}
