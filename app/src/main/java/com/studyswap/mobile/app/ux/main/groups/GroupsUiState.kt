package com.studyswap.mobile.app.ux.main.groups

import com.studyswap.mobile.app.data.source.remote.model.GroupData
import kotlinx.coroutines.flow.StateFlow

data class GroupsUiDataState(
    val isLoading: Boolean = false,
    val allGroups: List<GroupData> = emptyList(),
    val myGroups: List<GroupData> = emptyList(),
    val errorMessage: String? = null,
    val selectedFilter: String = "All Groups"
)

sealed class GroupsUiEvent {
    object LoadGroups : GroupsUiEvent()
    data class OnFilterChanged(val filter: String) : GroupsUiEvent()
    object OnDismissError : GroupsUiEvent()
}

data class GroupsUiState(
    val uiDataStateFlow: StateFlow<GroupsUiDataState>,
    val event: (GroupsUiEvent) -> Unit
)
