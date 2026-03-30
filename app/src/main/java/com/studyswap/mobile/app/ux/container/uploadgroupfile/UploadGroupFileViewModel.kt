package com.studyswap.mobile.app.ux.container.uploadgroupfile

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
class UploadGroupFileViewModel @Inject constructor(
    private val getUploadGroupFileUiStateUseCase: GetUploadGroupFileUiStateUseCase,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val groupId: String = savedStateHandle.get<String>("groupId") ?: "0"

    val uiState: UploadGroupFileUiState = getUploadGroupFileUiStateUseCase(
        context = context,
        groupId = groupId,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
