package com.studyswap.mobile.app.ux.startup.auth.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getSignupUiStateUseCase: GetSignupUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: SignupUiState = getSignupUiStateUseCase(
        context = context,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
