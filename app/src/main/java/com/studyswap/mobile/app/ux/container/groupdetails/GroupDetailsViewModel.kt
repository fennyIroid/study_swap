package com.studyswap.mobile.app.ux.container.groupdetails

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val getGroupDetailsUiStateUseCase: GetGroupDetailsUiStateUseCase,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val groupId: String = savedStateHandle.get<String>("groupId") ?: "0"

    val uiState: GroupDetailsUiState = getGroupDetailsUiStateUseCase(
        context = context,
        groupId = groupId,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
