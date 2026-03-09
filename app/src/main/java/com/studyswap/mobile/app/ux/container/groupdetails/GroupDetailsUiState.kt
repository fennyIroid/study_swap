package com.studyswap.mobile.app.ux.container.groupdetails

import com.studyswap.mobile.app.data.source.remote.model.GroupData
import kotlinx.coroutines.flow.StateFlow

data class PendingRequestItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val profileImageUrl: String? = null
)

data class MaterialItem(
    val id: String,
    val title: String,
    val type: MaterialType,
    val sizeMb: String,
    val rating: String?,
    val uploaderName: String,
    val downloadUrl: String? = null
)

enum class MaterialType { NOTES, BOOKS, QUESTION_PAPERS }

data class GroupDetailsUiDataState(
    val group: GroupData? = null,
    val memberCount: Int = 0,
    val isAdmin: Boolean = false,
    val joinCode: String = "",
    val unreadPendingCount: Int = 0,
    val pendingRequests: List<PendingRequestItem> = emptyList(),
    val materials: List<MaterialItem> = emptyList(),
    val materialsSearchQuery: String = "",
    val materialsTab: MaterialType = MaterialType.NOTES,
    val sortByLatest: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class GroupDetailsUiEvent {
    object OnBackClick : GroupDetailsUiEvent()
    object OnShareClick : GroupDetailsUiEvent()
    object OnMenuClick : GroupDetailsUiEvent()
    object OnManageGroupClick : GroupDetailsUiEvent()
    data class OnAcceptRequest(val request: PendingRequestItem) : GroupDetailsUiEvent()
    data class OnDeclineRequest(val request: PendingRequestItem) : GroupDetailsUiEvent()
    object OnCopyJoinCode : GroupDetailsUiEvent()
    object OnRefreshJoinCode : GroupDetailsUiEvent()
    data class OnSearchMaterials(val query: String) : GroupDetailsUiEvent()
    data class OnMaterialsTabChange(val tab: MaterialType) : GroupDetailsUiEvent()
    object OnSortChange : GroupDetailsUiEvent()
    data class OnDownloadMaterial(val material: MaterialItem) : GroupDetailsUiEvent()
    object OnAddMaterialClick : GroupDetailsUiEvent()
    object LoadGroupDetails : GroupDetailsUiEvent()
    object OnDismissError : GroupDetailsUiEvent()
}

data class GroupDetailsUiState(
    val uiDataStateFlow: StateFlow<GroupDetailsUiDataState>,
    val event: (GroupDetailsUiEvent) -> Unit
)
