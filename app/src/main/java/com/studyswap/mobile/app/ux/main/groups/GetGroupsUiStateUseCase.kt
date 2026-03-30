package com.studyswap.mobile.app.ux.main.groups

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetGroupsUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(GroupsUiDataState())

    operator fun invoke(
        coroutineScope: CoroutineScope
    ): GroupsUiState = GroupsUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(event, coroutineScope) }
    )

    private fun handleEvent(event: GroupsUiEvent, coroutineScope: CoroutineScope) {
        when (event) {
            is GroupsUiEvent.LoadGroups -> coroutineScope.launch {
                val silent = _uiDataStateFlow.value.allGroups.isNotEmpty() ||
                    _uiDataStateFlow.value.joinedGroups.isNotEmpty()
                fetchGroups(silent = silent)
            }
            is GroupsUiEvent.OnFilterChanged -> _uiDataStateFlow.update { it.copy(selectedFilter = event.filter) }
            is GroupsUiEvent.OnSendJoinRequest -> coroutineScope.launch {
                sendJoinRequest(event.groupId)
            }
            is GroupsUiEvent.OnDismissError -> _uiDataStateFlow.update { it.copy(errorMessage = null) }
            is GroupsUiEvent.OnDismissInfo -> _uiDataStateFlow.update { it.copy(infoMessage = null) }
        }
    }

    private suspend fun fetchGroups(silent: Boolean = false) {
        if (!silent) _uiDataStateFlow.update { it.copy(isLoading = true) }

        apiRepository.myGroups().collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success -> {
                    val buckets = result.data?.data
                    _uiDataStateFlow.update {
                        it.copy(
                            joinedGroups = buckets?.joined.orEmpty(),
                            pendingGroups = buckets?.pending.orEmpty(),
                            leftGroups = buckets?.left.orEmpty()
                        )
                    }
                }
                is NetworkResult.Error -> _uiDataStateFlow.update {
                    it.copy(errorMessage = result.message)
                }
                is NetworkResult.UnAuthenticated -> _uiDataStateFlow.update { it }
            }
        }

        apiRepository.getGroups().collect { result ->
            when (result) {
                is NetworkResult.Loading -> if (!silent) _uiDataStateFlow.update { it.copy(isLoading = true) }
                is NetworkResult.Success -> {
                    val allGroups = result.data?.data.orEmpty()
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, allGroups = allGroups, errorMessage = null)
                    }
                }
                is NetworkResult.Error -> _uiDataStateFlow.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                is NetworkResult.UnAuthenticated -> _uiDataStateFlow.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun sendJoinRequest(groupId: Int) {
        _uiDataStateFlow.update {
            it.copy(requestedGroupIds = it.requestedGroupIds + groupId, errorMessage = null, infoMessage = null)
        }
        apiRepository.sendGroupRequest(groupId).collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success ->
                    _uiDataStateFlow.update {
                        it.copy(infoMessage = "Join request sent")
                    }
                is NetworkResult.Error ->
                    _uiDataStateFlow.update {
                        it.copy(
                            requestedGroupIds = it.requestedGroupIds - groupId,
                            errorMessage = result.message
                        )
                    }
                is NetworkResult.UnAuthenticated ->
                    _uiDataStateFlow.update {
                        it.copy(
                            requestedGroupIds = it.requestedGroupIds - groupId,
                            errorMessage = "Session expired. Please log in again."
                        )
                    }
            }
        }
    }
}
