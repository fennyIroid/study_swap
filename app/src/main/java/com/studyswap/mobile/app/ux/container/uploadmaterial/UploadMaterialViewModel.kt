package com.studyswap.mobile.app.ux.container.uploadmaterial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadMaterialViewModel @Inject constructor(
    private val getUploadMaterialUiStateUseCase: GetUploadMaterialUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: UploadMaterialUiState = getUploadMaterialUiStateUseCase(
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
