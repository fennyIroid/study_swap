package com.studyswap.mobile.app.ux.container.groupdetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.ux.container.uploadmaterial.UploadMaterialRoute
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
                val requestId = event.request.id.toIntOrNull()
                if (requestId == null) {
                    _uiDataStateFlow.update {
                        it.copy(errorMessage = "Invalid request id")
                    }
                    return
                }
                // Optimistically remove from list
                _uiDataStateFlow.update { state ->
                    state.copy(
                        pendingRequests = state.pendingRequests.filter { it.id != event.request.id },
                        unreadPendingCount = (state.unreadPendingCount - 1).coerceAtLeast(0)
                    )
                }
                coroutineScope.launch {
                    acceptGroupRequest(requestId, event.request)
                }
            }
            is GroupDetailsUiEvent.OnDeclineRequest -> {
                val requestId = event.request.id.toIntOrNull()
                if (requestId == null) {
                    _uiDataStateFlow.update {
                        it.copy(errorMessage = "Invalid request id")
                    }
                    return
                }
                // Optimistically remove from list
                _uiDataStateFlow.update { state ->
                    state.copy(
                        pendingRequests = state.pendingRequests.filter { it.id != event.request.id },
                        unreadPendingCount = (state.unreadPendingCount - 1).coerceAtLeast(0)
                    )
                }
                coroutineScope.launch {
                    rejectGroupRequest(requestId, event.request)
                }
            }
            is GroupDetailsUiEvent.OnCopyJoinCode -> {
                val code = _uiDataStateFlow.value.joinCode
                (context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.setPrimaryClip(
                    ClipData.newPlainText("Join code", code)
                )
            }
            is GroupDetailsUiEvent.OnRefreshJoinCode -> {
                val idInt = _uiDataStateFlow.value.group?.id ?: groupId.toIntOrNull() ?: return
                coroutineScope.launch {
                    refreshInviteLink(idInt)
                }
            }
            is GroupDetailsUiEvent.OnSearchMaterials ->
                _uiDataStateFlow.update { it.copy(materialsSearchQuery = event.query) }
            is GroupDetailsUiEvent.OnMaterialsTabChange ->
                _uiDataStateFlow.update { it.copy(materialsTab = event.tab) }
            is GroupDetailsUiEvent.OnSortChange ->
                _uiDataStateFlow.update { it.copy(sortByLatest = !it.sortByLatest) }
            is GroupDetailsUiEvent.OnDownloadMaterial -> { /* TODO: download */ }
            is GroupDetailsUiEvent.OnAddMaterialClick ->
                navigate(NavigationAction.Navigate(UploadMaterialRoute.createRoute()))
            is GroupDetailsUiEvent.OnSettingsChanged -> {
                val current = _uiDataStateFlow.value
                if (!current.isAdmin || current.group?.id == null) return
                _uiDataStateFlow.update { it.copy(settings = event.settings) }
                coroutineScope.launch {
                    updateGroupSettings(
                        groupIdInt = current.group.id ?: return@launch,
                        settings = event.settings
                    )
                }
            }
            is GroupDetailsUiEvent.OnMemberRoleChange -> {
                val current = _uiDataStateFlow.value
                val groupIdInt = current.group?.id ?: return
                if (!current.isAdmin) return
                val userIdInt = event.member.userId.toIntOrNull() ?: return

                // optimistic UI: update member list
                val prevMembers = current.members
                val updatedMembers = prevMembers.map {
                    if (it.userId == event.member.userId) it.copy(role = event.newRole) else it
                }
                _uiDataStateFlow.update { it.copy(members = updatedMembers) }

                coroutineScope.launch {
                    updateMemberRole(
                        groupIdInt = groupIdInt,
                        userIdInt = userIdInt,
                        memberBefore = event.member,
                        newRole = event.newRole
                    )
                }
            }
            is GroupDetailsUiEvent.OnRemoveMember -> {
                val current = _uiDataStateFlow.value
                val groupIdInt = current.group?.id ?: return
                if (!current.isAdmin) return
                val userIdInt = event.member.userId.toIntOrNull() ?: return

                // Optimistically remove member and adjust count
                val previousMembers = current.members
                val updatedMembers = previousMembers.filter { it.userId != event.member.userId }
                _uiDataStateFlow.update {
                    it.copy(
                        members = updatedMembers,
                        memberCount = (it.memberCount - 1).coerceAtLeast(0)
                    )
                }

                coroutineScope.launch {
                    removeMember(
                        groupIdInt = groupIdInt,
                        userIdInt = userIdInt,
                        member = event.member,
                        previousMembers = previousMembers
                    )
                }
            }
            is GroupDetailsUiEvent.OnLeaveGroupClick -> {
                val idInt = _uiDataStateFlow.value.group?.id ?: return
                coroutineScope.launch {
                    leaveGroupAndNavigateBack(idInt, navigate)
                }
            }
            is GroupDetailsUiEvent.LoadGroupDetails -> coroutineScope.launch {
                loadGroupDetails(groupId)
            }
            is GroupDetailsUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }

    private suspend fun loadGroupDetails(groupId: String) {
        val idInt = groupId.toIntOrNull()
        if (idInt == null) {
            _uiDataStateFlow.update {
                it.copy(isLoading = false, errorMessage = "Invalid group id")
            }
            return
        }

        _uiDataStateFlow.update { it.copy(isLoading = true) }
        apiRepository.getGroupDetails(idInt).collect { result ->
            when (result) {
                is NetworkResult.Loading -> _uiDataStateFlow.update { it.copy(isLoading = true) }
                is NetworkResult.Success -> {
                    val payload = result.data?.data
                    val group = payload?.group
                    if (group != null) {
                        val settings = payload.settings
                        val members = payload.members.orEmpty()

                        val settingsUi = settings?.let {
                            GroupSettingsUi(
                                allowMemberInvite = (it.allowMemberInvite ?: 0) == 1,
                                allowFileShare = (it.allowFileShare ?: 0) == 1,
                                allowChat = (it.allowChat ?: 0) == 1
                            )
                        }

                        val membersUi = members.map { member ->
                            GroupMemberUi(
                                id = (member.id ?: 0).toString(),
                                userId = (member.userId ?: 0).toString(),
                                role = member.role.orEmpty(),
                                joinedAt = member.joinedAt.orEmpty()
                            )
                        }

                        val memberCount = members.size
                        val isAdmin = members.any { it.role.equals("admin", ignoreCase = true) }
                        val joinCodeFromLink = group.inviteLink
                            ?.substringAfterLast("/")
                            ?.takeIf { it.isNotBlank() }
                            ?: ""

                        _uiDataStateFlow.update {
                            it.copy(
                                isLoading = false,
                                group = group,
                                memberCount = memberCount,
                                isAdmin = isAdmin,
                                joinCode = joinCodeFromLink,
                                unreadPendingCount = 0,
                                pendingRequests = emptyList(),
                                materials = emptyList(),
                                settings = settingsUi,
                                members = membersUi
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

    private suspend fun updateGroupSettings(
        groupIdInt: Int,
        settings: GroupSettingsUi
    ) {
        apiRepository.setGroupSettings(
            groupId = groupIdInt,
            allowMemberInvite = settings.allowMemberInvite,
            allowFileShare = settings.allowFileShare,
            allowChat = settings.allowChat
        ).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // Optionally show a subtle loading state; skipping to avoid flicker
                }
                is NetworkResult.Success -> {
                    // Already updated local state optimistically; nothing more required
                }
                is NetworkResult.Error -> {
                    _uiDataStateFlow.update {
                        it.copy(errorMessage = result.message)
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Session expired") }
                }
            }
        }
    }

    private suspend fun acceptGroupRequest(
        requestId: Int,
        request: PendingRequestItem
    ) {
        apiRepository.acceptGroupRequest(requestId).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // no-op; avoid flicker
                }
                is NetworkResult.Success -> {
                    // request approved; nothing more to change locally for now
                }
                is NetworkResult.Error -> {
                    // Restore request into list on failure
                    _uiDataStateFlow.update { state ->
                        state.copy(
                            pendingRequests = state.pendingRequests + request,
                            unreadPendingCount = state.unreadPendingCount + 1,
                            errorMessage = result.message
                        )
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update { state ->
                        state.copy(
                            pendingRequests = state.pendingRequests + request,
                            unreadPendingCount = state.unreadPendingCount + 1,
                            errorMessage = "Session expired"
                        )
                    }
                }
            }
        }
    }

    private suspend fun rejectGroupRequest(
        requestId: Int,
        request: PendingRequestItem
    ) {
        apiRepository.rejectGroupRequest(requestId).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // no-op
                }
                is NetworkResult.Success -> {
                    // request rejected; pending list already updated
                }
                is NetworkResult.Error -> {
                    _uiDataStateFlow.update { state ->
                        state.copy(
                            pendingRequests = state.pendingRequests + request,
                            unreadPendingCount = state.unreadPendingCount + 1,
                            errorMessage = result.message
                        )
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update { state ->
                        state.copy(
                            pendingRequests = state.pendingRequests + request,
                            unreadPendingCount = state.unreadPendingCount + 1,
                            errorMessage = "Session expired"
                        )
                    }
                }
            }
        }
    }

    private suspend fun updateMemberRole(
        groupIdInt: Int,
        userIdInt: Int,
        memberBefore: GroupMemberUi,
        newRole: String
    ) {
        apiRepository.setGroupMemberRole(
            groupId = groupIdInt,
            userId = userIdInt,
            role = newRole
        ).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // no-op
                }
                is NetworkResult.Success -> {
                    // role updated on backend; optimistic UI already applied
                }
                is NetworkResult.Error -> {
                    // revert to previous role on failure
                    _uiDataStateFlow.update { state ->
                        state.copy(
                            members = state.members.map {
                                if (it.userId == memberBefore.userId) it.copy(role = memberBefore.role) else it
                            },
                            errorMessage = result.message
                        )
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update { state ->
                        state.copy(
                            members = state.members.map {
                                if (it.userId == memberBefore.userId) it.copy(role = memberBefore.role) else it
                            },
                            errorMessage = "Session expired"
                        )
                    }
                }
            }
        }
    }

    private suspend fun removeMember(
        groupIdInt: Int,
        userIdInt: Int,
        member: GroupMemberUi,
        previousMembers: List<GroupMemberUi>
    ) {
        apiRepository.removeGroupMember(
            groupId = groupIdInt,
            userId = userIdInt
        ).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // no-op
                }
                is NetworkResult.Success -> {
                    // member removed on backend; optimistic UI already applied
                }
                is NetworkResult.Error -> {
                    // restore members and count on failure
                    _uiDataStateFlow.update {
                        it.copy(
                            members = previousMembers,
                            memberCount = previousMembers.size,
                            errorMessage = result.message
                        )
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update {
                        it.copy(
                            members = previousMembers,
                            memberCount = previousMembers.size,
                            errorMessage = "Session expired"
                        )
                    }
                }
            }
        }
    }

    private suspend fun leaveGroupAndNavigateBack(
        groupIdInt: Int,
        navigate: (NavigationAction) -> Unit
    ) {
        apiRepository.leaveGroup(groupIdInt).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiDataStateFlow.update { it.copy(isLoading = true) }
                }
                is NetworkResult.Success -> {
                    _uiDataStateFlow.update { it.copy(isLoading = false) }
                    navigate(NavigationAction.Pop())
                }
                is NetworkResult.Error -> {
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = "Session expired")
                    }
                }
            }
        }
    }

    private suspend fun refreshInviteLink(groupIdInt: Int) {
        apiRepository.generateInviteLink(groupIdInt).collect { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiDataStateFlow.update { it.copy(isLoading = true) }
                }
                is NetworkResult.Success -> {
                    val groupFromResponse = result.data?.data
                    val newInvite = groupFromResponse?.inviteLink
                    _uiDataStateFlow.update { current ->
                        val updatedGroup = current.group?.copy(
                            inviteLink = newInvite ?: current.group.inviteLink
                        )
                        current.copy(
                            isLoading = false,
                            group = updatedGroup,
                            joinCode = newInvite
                                ?.substringAfterLast("/")
                                ?.takeIf { it.isNotBlank() }
                                ?: current.joinCode
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = "Session expired")
                    }
                }
            }
        }
    }
}
