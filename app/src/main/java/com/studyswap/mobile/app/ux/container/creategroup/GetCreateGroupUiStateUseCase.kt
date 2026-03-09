package com.studyswap.mobile.app.ux.container.creategroup

import android.content.Context
import android.net.Uri
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
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
            is CreateGroupUiEvent.OnSemesterChange -> _uiDataStateFlow.update { it.copy(semester = event.semester) }
            is CreateGroupUiEvent.OnDescriptionChange -> _uiDataStateFlow.update { it.copy(description = event.description) }
            is CreateGroupUiEvent.OnPrivateChange -> _uiDataStateFlow.update { it.copy(isPrivate = event.isPrivate) }
            is CreateGroupUiEvent.OnIconSelected -> _uiDataStateFlow.update { it.copy(groupIconUri = event.uri) }
            is CreateGroupUiEvent.OnCreateClick -> {
                val state = _uiDataStateFlow.value
                if (state.groupName.isBlank() || state.subject.isBlank()) {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Group Name and Subject are required") }
                    return
                }

                coroutineScope.launch {
                    val file = getFileFromUri(context, state.groupIconUri)
                    val isPublic = if (state.isPrivate) 0 else 1
                    val approvalReq = if (state.isPrivate) 1 else 0

                    apiRepository.createGroup(
                        name = state.groupName,
                        description = state.description,
                        groupType = "study",
                        subject = state.subject,
                        maxMembers = 10,
                        isPublic = isPublic,
                        approvalRequired = approvalReq,
                        groupIcon = file
                    ).collect { result ->
                        when (result) {
                            is NetworkResult.Loading -> _uiDataStateFlow.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> {
                                _uiDataStateFlow.update { it.copy(isLoading = false, isSuccess = true) }
                                navigate(NavigationAction.Pop()) 
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
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri?): File? {
        if (uri == null) return null
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File(context.cacheDir, "upload_icon_${System.currentTimeMillis()}.jpg")
            val outputStream = java.io.FileOutputStream(tempFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            tempFile
        } catch (e: Exception) {
            null
        }
    }
}
