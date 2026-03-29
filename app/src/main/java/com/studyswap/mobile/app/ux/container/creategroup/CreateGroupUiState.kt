package com.studyswap.mobile.app.ux.container.creategroup

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

data class CreateGroupUiDataState(
    val groupName: String = "",
    val subject: String = "",
    val maxMembers: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

sealed class CreateGroupUiEvent {
    data class OnGroupNameChange(val name: String) : CreateGroupUiEvent()
    data class OnSubjectChange(val subject: String) : CreateGroupUiEvent()
    data class OnMaxMembersChange(val maxMembers: String) : CreateGroupUiEvent()
    data class OnDescriptionChange(val description: String) : CreateGroupUiEvent()
    object OnCreateClick : CreateGroupUiEvent()
    object OnBackClick : CreateGroupUiEvent()
    object OnDismissError : CreateGroupUiEvent()
}

data class CreateGroupUiState(
    val uiDataStateFlow: StateFlow<CreateGroupUiDataState>,
    val event: (CreateGroupUiEvent) -> Unit
)
