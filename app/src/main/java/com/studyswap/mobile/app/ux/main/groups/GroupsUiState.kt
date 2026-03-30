package com.studyswap.mobile.app.ux.main.groups

import com.studyswap.mobile.app.data.source.remote.model.GroupData
import kotlinx.coroutines.flow.StateFlow

data class GroupsUiDataState(
    val isLoading: Boolean = false,
    val allGroups: List<GroupData> = emptyList(),
    val joinedGroups: List<GroupData> = emptyList(),
    val pendingGroups: List<GroupData> = emptyList(),
    val leftGroups: List<GroupData> = emptyList(),
    val infoMessage: String? = null,
    val errorMessage: String? = null,
    val selectedFilter: String = "All Groups",
    val requestedGroupIds: Set<Int> = emptySet()
)

sealed class GroupsUiEvent {
    object LoadGroups : GroupsUiEvent()
    data class OnFilterChanged(val filter: String) : GroupsUiEvent()
    data class OnSendJoinRequest(val groupId: Int) : GroupsUiEvent()
    object OnDismissError : GroupsUiEvent()
    object OnDismissInfo : GroupsUiEvent()
}

data class GroupsUiState(
    val uiDataStateFlow: StateFlow<GroupsUiDataState>,
    val event: (GroupsUiEvent) -> Unit
)
