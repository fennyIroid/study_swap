package com.studyswap.mobile.app.ux.container.joingroup

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.GroupData
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.ux.container.creategroup.CreateGroupRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetJoinGroupUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(JoinGroupUiDataState())

    private val categoryFilters = listOf("All Groups", "Calculus", "Biology", "Literature", "Chemistry", "History")

    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): JoinGroupUiState = JoinGroupUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        event: JoinGroupUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is JoinGroupUiEvent.OnInviteCodeChange ->
                _uiDataStateFlow.update { it.copy(inviteCode = event.code) }
            is JoinGroupUiEvent.OnJoinWithCodeClick -> {
                val code = _uiDataStateFlow.value.inviteCode.trim()
                if (code.isBlank()) {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Please enter an invite code") }
                    return
                }
                coroutineScope.launch {
                    _uiDataStateFlow.update { it.copy(isJoinByCodeLoading = true) }
                    // TODO: replace with API joinByInviteCode(code) when available
                    kotlinx.coroutines.delay(800)
                    _uiDataStateFlow.update {
                        it.copy(
                            isJoinByCodeLoading = false,
                            errorMessage = "Join by code is not yet available"
                        )
                    }
                }
            }
            is JoinGroupUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            is JoinGroupUiEvent.OnSearchChange ->
                _uiDataStateFlow.update { it.copy(searchQuery = event.query) }
            is JoinGroupUiEvent.OnCategoryChange ->
                _uiDataStateFlow.update { it.copy(selectedCategory = event.category) }
            is JoinGroupUiEvent.OnRequestJoin -> {
                val id = event.group.id ?: return
                // Optimistically mark as requested to disable button immediately
                _uiDataStateFlow.update {
                    it.copy(requestedGroupIds = it.requestedGroupIds + id)
                }
                coroutineScope.launch {
                    sendJoinRequest(id)
                }
            }
            is JoinGroupUiEvent.OnViewAllClick -> {
                // Could navigate to full browse or expand list; for now no-op or same screen
            }
            is JoinGroupUiEvent.OnCreateGroupClick ->
                navigate(NavigationAction.Navigate(CreateGroupRoute.createRoute()))
            is JoinGroupUiEvent.LoadGroups -> coroutineScope.launch { fetchGroups() }
            is JoinGroupUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
            is JoinGroupUiEvent.OnPasteOrQrClick -> {
                // TODO: open clipboard paste or QR scanner
            }
        }
    }

    private suspend fun fetchGroups() {
        _uiDataStateFlow.update { it.copy(isLoading = true) }
        apiRepository.getGroups().collect { result ->
            when (result) {
                is NetworkResult.Loading -> _uiDataStateFlow.update { it.copy(isLoading = true) }
                is NetworkResult.Success -> {
                    val all = result.data?.data ?: emptyList()
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, browseGroups = all)
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

    private suspend fun sendJoinRequest(groupId: Int) {
        apiRepository.sendGroupRequest(groupId).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // no global spinner; could add per-item loading if needed
                }
                is NetworkResult.Success -> {
                    // nothing else to do; requestedGroupIds already updated
                }
                is NetworkResult.Error -> {
                    _uiDataStateFlow.update {
                        it.copy(
                            errorMessage = result.message,
                            requestedGroupIds = it.requestedGroupIds - groupId
                        )
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update {
                        it.copy(
                            errorMessage = "Session expired. Please log in again.",
                            requestedGroupIds = it.requestedGroupIds - groupId
                        )
                    }
                }
            }
        }
    }

}
