package com.studyswap.mobile.app.ux.main.home

import com.studyswap.mobile.app.data.source.remote.model.GroupData
import kotlinx.coroutines.flow.StateFlow

data class HomeUiDataState(
    val isLoading: Boolean = false,
    val allGroups: List<GroupData> = emptyList(),
    val myGroups: List<GroupData> = emptyList(),
    val errorMessage: String? = null,
    val selectedFilter: String = "All"
)

sealed class HomeUiEvent {
    object LoadGroups : HomeUiEvent()
    data class OnFilterChanged(val filter: String) : HomeUiEvent()
    object OnDismissError : HomeUiEvent()
}

data class HomeUiState(
    val uiDataStateFlow: StateFlow<HomeUiDataState>,
    val event: (HomeUiEvent) -> Unit
)
