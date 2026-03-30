package com.studyswap.mobile.app.ux.container.creategroup

import android.content.Context
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.utils.asMultipartPart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCreateGroupUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(CreateGroupUiDataState())

    operator fun invoke(
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): CreateGroupUiState = CreateGroupUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(context, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        event: CreateGroupUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is CreateGroupUiEvent.OnGroupNameChange -> _uiDataStateFlow.update { it.copy(groupName = event.name) }
            is CreateGroupUiEvent.OnSubjectChange -> _uiDataStateFlow.update { it.copy(subject = event.subject) }
            is CreateGroupUiEvent.OnMaxMembersChange -> _uiDataStateFlow.update { it.copy(maxMembers = event.maxMembers) }
            is CreateGroupUiEvent.OnDescriptionChange -> _uiDataStateFlow.update { it.copy(description = event.description) }
            is CreateGroupUiEvent.OnGroupIconSelected ->
                _uiDataStateFlow.update {
                    it.copy(groupIconUri = event.uri, groupIconName = event.fileName)
                }
            CreateGroupUiEvent.OnClearGroupIcon ->
                _uiDataStateFlow.update { it.copy(groupIconUri = null, groupIconName = null) }
            is CreateGroupUiEvent.OnCreateClick -> {
                val state = _uiDataStateFlow.value
                if (state.groupName.isBlank() || state.subject.isBlank()) {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Group Name and Subject are required") }
                    return
                }
                val maxMembersInt = state.maxMembers.toIntOrNull() ?: 10

                coroutineScope.launch {
                    val iconPart = state.groupIconUri?.let { uri ->
                        try {
                            uri.asMultipartPart(context, "group_icon")
                        } catch (_: Exception) {
                            null
                        }
                    }
                    apiRepository.createGroup(
                        name = state.groupName,
                        description = state.description,
                        groupType = "study",
                        subject = state.subject,
                        maxMembers = maxMembersInt,
                        groupIcon = iconPart
                    ).collect { result ->
                        when (result) {
                            is NetworkResult.Loading -> _uiDataStateFlow.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> {
                                val code = result.data?.data?.invitationCode
                                _uiDataStateFlow.update {
                                    it.copy(
                                        isLoading = false,
                                        isSuccess = true,
                                        invitationCode = code
                                    )
                                }
                                if (code.isNullOrBlank()) {
                                    navigate(NavigationAction.Pop())
                                }
                            }
                            is NetworkResult.Error -> {
                                _uiDataStateFlow.update { it.copy(isLoading = false, errorMessage = result.message) }
                            }
                            is NetworkResult.UnAuthenticated -> {
                                _uiDataStateFlow.update { it.copy(isLoading = false) }
                            }
                        }
                    }
                }
            }
            is CreateGroupUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            is CreateGroupUiEvent.OnDismissError -> _uiDataStateFlow.update { it.copy(errorMessage = null) }
            is CreateGroupUiEvent.OnDismissInviteDialog -> {
                _uiDataStateFlow.update { it.copy(invitationCode = null, isSuccess = false) }
                navigate(NavigationAction.Pop())
            }
        }
    }
}
