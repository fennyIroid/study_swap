package com.studyswap.mobile.app.ux.container.creategroup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val getCreateGroupUiStateUseCase: GetCreateGroupUiStateUseCase,
    @ApplicationContext private val context: Context
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: CreateGroupUiState = getCreateGroupUiStateUseCase(
        context = context,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
