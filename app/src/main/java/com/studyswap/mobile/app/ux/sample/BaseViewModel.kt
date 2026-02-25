package com.studyswap.mobile.app.ux.sample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Sample template: ViewModel that exposes UI state and handles navigation.
 * Copy and rename (e.g. HomeViewModel), implement ViewModelNav by ViewModelNavImpl() when screen navigates.
 */
@HiltViewModel
class BaseViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getBaseUiStateUseCase: GetBaseUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: BaseUiState = getBaseUiStateUseCase(
        context = context,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
