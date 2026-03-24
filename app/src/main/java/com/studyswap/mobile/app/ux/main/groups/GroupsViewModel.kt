package com.studyswap.mobile.app.ux.main.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getGroupsUiStateUseCase: GetGroupsUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: GroupsUiState = getGroupsUiStateUseCase(viewModelScope)
}
