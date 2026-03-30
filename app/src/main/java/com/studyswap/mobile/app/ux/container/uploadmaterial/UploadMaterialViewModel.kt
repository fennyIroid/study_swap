package com.studyswap.mobile.app.ux.container.uploadmaterial

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class UploadMaterialViewModel @Inject constructor(
    private val getUploadMaterialUiStateUseCase: GetUploadMaterialUiStateUseCase,
    @ApplicationContext private val context: Context
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: UploadMaterialUiState = getUploadMaterialUiStateUseCase(
        context = context,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
