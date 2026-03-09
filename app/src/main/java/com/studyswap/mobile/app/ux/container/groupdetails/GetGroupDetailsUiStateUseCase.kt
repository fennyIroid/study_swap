package com.studyswap.mobile.app.ux.container.groupdetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetGroupDetailsUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(GroupDetailsUiDataState())

    operator fun invoke(
        context: Context,
        groupId: String,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): GroupDetailsUiState = GroupDetailsUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(context, groupId, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        groupId: String,
        event: GroupDetailsUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is GroupDetailsUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            is GroupDetailsUiEvent.OnShareClick -> { /* TODO: share group */ }
            is GroupDetailsUiEvent.OnMenuClick -> { /* TODO: show menu */ }
            is GroupDetailsUiEvent.OnManageGroupClick -> { /* TODO: navigate to manage group */ }
            is GroupDetailsUiEvent.OnAcceptRequest -> {
                _uiDataStateFlow.update { state ->
                    state.copy(
                        pendingRequests = state.pendingRequests.filter { it.id != event.request.id },
                        unreadPendingCount = (state.unreadPendingCount - 1).coerceAtLeast(0)
                    )
                }
            }
            is GroupDetailsUiEvent.OnDeclineRequest -> {
                _uiDataStateFlow.update { state ->
                    state.copy(
                        pendingRequests = state.pendingRequests.filter { it.id != event.request.id },
                        unreadPendingCount = (state.unreadPendingCount - 1).coerceAtLeast(0)
                    )
                }
            }
            is GroupDetailsUiEvent.OnCopyJoinCode -> {
                val code = _uiDataStateFlow.value.joinCode
                (context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.setPrimaryClip(
                    ClipData.newPlainText("Join code", code)
                )
            }
            is GroupDetailsUiEvent.OnRefreshJoinCode -> { /* TODO: API refresh join code */ }
            is GroupDetailsUiEvent.OnSearchMaterials ->
                _uiDataStateFlow.update { it.copy(materialsSearchQuery = event.query) }
            is GroupDetailsUiEvent.OnMaterialsTabChange ->
                _uiDataStateFlow.update { it.copy(materialsTab = event.tab) }
            is GroupDetailsUiEvent.OnSortChange ->
                _uiDataStateFlow.update { it.copy(sortByLatest = !it.sortByLatest) }
            is GroupDetailsUiEvent.OnDownloadMaterial -> { /* TODO: download */ }
            is GroupDetailsUiEvent.OnAddMaterialClick -> { /* TODO: add material */ }
            is GroupDetailsUiEvent.LoadGroupDetails -> coroutineScope.launch {
                loadGroupDetails(groupId)
            }
            is GroupDetailsUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }

    private suspend fun loadGroupDetails(groupId: String) {
        _uiDataStateFlow.update { it.copy(isLoading = true) }
        apiRepository.getGroups().collect { result ->
            when (result) {
                is NetworkResult.Loading -> _uiDataStateFlow.update { it.copy(isLoading = true) }
                is NetworkResult.Success -> {
                    val group = result.data?.data?.find { it.id == groupId.toIntOrNull() }
                    if (group != null) {
                        _uiDataStateFlow.update {
                            it.copy(
                                isLoading = false,
                                group = group,
                                memberCount = group.maxMembers ?: 0,
                                isAdmin = true,
                                joinCode = "XJ9-22B",
                                unreadPendingCount = 3,
                                pendingRequests = listOf(
                                    PendingRequestItem(
                                        id = "1",
                                        name = "Alex Smith",
                                        subtitle = "CS • 2nd Year"
                                    )
                                ),
                                materials = listOf(
                                    MaterialItem(
                                        id = "1",
                                        title = "Lecture 4 - Derivatives",
                                        type = MaterialType.NOTES,
                                        sizeMb = "3.2",
                                        rating = "4.8",
                                        uploaderName = "John Doe"
                                    ),
                                    MaterialItem(
                                        id = "2",
                                        title = "Week 2 Practice Set",
                                        type = MaterialType.NOTES,
                                        sizeMb = "1.4",
                                        rating = "4.5",
                                        uploaderName = "Sarah L."
                                    ),
                                    MaterialItem(
                                        id = "3",
                                        title = "Board Notes - 12/10",
                                        type = MaterialType.NOTES,
                                        sizeMb = "5.1",
                                        rating = null,
                                        uploaderName = "Mike R."
                                    )
                                )
                            )
                        }
                    } else {
                        _uiDataStateFlow.update {
                            it.copy(isLoading = false, errorMessage = "Group not found")
                        }
                    }
                }
                is NetworkResult.Error ->
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                is NetworkResult.UnAuthenticated ->
                    _uiDataStateFlow.update { it.copy(isLoading = false) }
            }
        }
    }
}
