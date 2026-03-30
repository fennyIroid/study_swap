package com.studyswap.mobile.app.ux.container.uploadgroupfile

import android.content.Context
import android.net.Uri
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.utils.asMultipartPart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUploadGroupFileUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(UploadGroupFileUiDataState())

    operator fun invoke(
        context: Context,
        groupId: String,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): UploadGroupFileUiState = UploadGroupFileUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(context, groupId, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        groupId: String,
        event: UploadGroupFileUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is UploadGroupFileUiEvent.OnTitleChange ->
                _uiDataStateFlow.update { it.copy(title = event.value) }
            is UploadGroupFileUiEvent.OnCategoryChange ->
                _uiDataStateFlow.update { it.copy(category = event.value) }
            is UploadGroupFileUiEvent.OnFileSelected ->
                _uiDataStateFlow.update {
                    it.copy(selectedFileUri = event.uri, selectedFileName = event.fileName, errorMessage = null)
                }
            is UploadGroupFileUiEvent.OnThumbnailSelected ->
                _uiDataStateFlow.update {
                    it.copy(thumbnailUri = event.uri, thumbnailName = event.fileName)
                }
            UploadGroupFileUiEvent.OnUploadClick -> {
                val state = _uiDataStateFlow.value
                val idInt = groupId.toIntOrNull()
                when {
                    idInt == null || idInt <= 0 ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Invalid group") }
                    state.title.isBlank() ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Title is required") }
                    state.category.isBlank() ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Category is required") }
                    state.selectedFileUri == null ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Please attach a file") }
                    else -> coroutineScope.launch {
                        upload(context, idInt, state, navigate)
                    }
                }
            }
            UploadGroupFileUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            UploadGroupFileUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }

    private suspend fun upload(
        context: Context,
        groupId: Int,
        state: UploadGroupFileUiDataState,
        navigate: (NavigationAction) -> Unit
    ) {
        val fileUri = state.selectedFileUri ?: return
        _uiDataStateFlow.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val filePart = fileUri.asMultipartPart(context, "file")
            val thumbPart = state.thumbnailUri?.takeIf { it != Uri.EMPTY }
                ?.asMultipartPart(context, "thumbnail")
            apiRepository.uploadGroupFile(
                groupId = groupId,
                title = state.title.trim(),
                category = state.category.trim(),
                file = filePart,
                thumbnail = thumbPart
            ).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> Unit
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
                            it.copy(isLoading = false, errorMessage = "Please sign in to upload.")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _uiDataStateFlow.update {
                it.copy(isLoading = false, errorMessage = e.message ?: "Upload failed")
            }
        }
    }
}
