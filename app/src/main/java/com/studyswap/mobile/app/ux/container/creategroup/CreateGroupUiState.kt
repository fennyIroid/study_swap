package com.studyswap.mobile.app.ux.container.creategroup

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

data class CreateGroupUiDataState(
    val groupName: String = "",
    val subject: String = "",
    val maxMembers: String = "",
    val description: String = "",
    val groupIconUri: Uri? = null,
    val groupIconName: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val invitationCode: String? = null,
    val errorMessage: String? = null
)

sealed class CreateGroupUiEvent {
    data class OnGroupNameChange(val name: String) : CreateGroupUiEvent()
    data class OnSubjectChange(val subject: String) : CreateGroupUiEvent()
    data class OnMaxMembersChange(val maxMembers: String) : CreateGroupUiEvent()
    data class OnDescriptionChange(val description: String) : CreateGroupUiEvent()
    data class OnGroupIconSelected(val uri: Uri?, val fileName: String?) : CreateGroupUiEvent()
    object OnClearGroupIcon : CreateGroupUiEvent()
    object OnCreateClick : CreateGroupUiEvent()
    object OnBackClick : CreateGroupUiEvent()
    object OnDismissError : CreateGroupUiEvent()
    object OnDismissInviteDialog : CreateGroupUiEvent()
}

data class CreateGroupUiState(
    val uiDataStateFlow: StateFlow<CreateGroupUiDataState>,
    val event: (CreateGroupUiEvent) -> Unit
)
