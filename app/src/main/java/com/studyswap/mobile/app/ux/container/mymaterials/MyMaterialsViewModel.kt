package com.studyswap.mobile.app.ux.container.mymaterials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyMaterialsViewModel @Inject constructor(
    private val getMyMaterialsUiStateUseCase: GetMyMaterialsUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: MyMaterialsUiState = getMyMaterialsUiStateUseCase(
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
