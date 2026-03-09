package com.studyswap.mobile.app.ux.container.joingroup

import com.studyswap.mobile.app.data.source.remote.model.GroupData
import kotlinx.coroutines.flow.StateFlow

data class JoinGroupUiDataState(
    val inviteCode: String = "",
    val searchQuery: String = "",
    val selectedCategory: String = "All Groups",
    val browseGroups: List<GroupData> = emptyList(),
    val requestedGroupIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val isJoinByCodeLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class JoinGroupUiEvent {
    data class OnInviteCodeChange(val code: String) : JoinGroupUiEvent()
    object OnJoinWithCodeClick : JoinGroupUiEvent()
    object OnBackClick : JoinGroupUiEvent()
    data class OnSearchChange(val query: String) : JoinGroupUiEvent()
    data class OnCategoryChange(val category: String) : JoinGroupUiEvent()
    data class OnRequestJoin(val group: GroupData) : JoinGroupUiEvent()
    object OnViewAllClick : JoinGroupUiEvent()
    object OnCreateGroupClick : JoinGroupUiEvent()
    object LoadGroups : JoinGroupUiEvent()
    object OnDismissError : JoinGroupUiEvent()
    object OnPasteOrQrClick : JoinGroupUiEvent()
}

data class JoinGroupUiState(
    val uiDataStateFlow: StateFlow<JoinGroupUiDataState>,
    val event: (JoinGroupUiEvent) -> Unit
)
