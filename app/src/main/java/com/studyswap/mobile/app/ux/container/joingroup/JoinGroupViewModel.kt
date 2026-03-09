package com.studyswap.mobile.app.ux.container.joingroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val getJoinGroupUiStateUseCase: GetJoinGroupUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: JoinGroupUiState = getJoinGroupUiStateUseCase(
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
