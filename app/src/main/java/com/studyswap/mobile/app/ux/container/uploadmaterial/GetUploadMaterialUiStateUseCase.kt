package com.studyswap.mobile.app.ux.container.uploadmaterial

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

class GetUploadMaterialUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(UploadMaterialUiDataState())

    operator fun invoke(
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): UploadMaterialUiState = UploadMaterialUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(context, event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        event: UploadMaterialUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            UploadMaterialUiEvent.Load -> coroutineScope.launch {
                apiRepository.getCategories().collect { result ->
                    when (result) {
                        is NetworkResult.Success ->
                            _uiDataStateFlow.update {
                                it.copy(suggestedCategories = result.data?.data.orEmpty())
                            }
                        else -> Unit
                    }
                }
            }
            is UploadMaterialUiEvent.OnMaterialTitleChange ->
                _uiDataStateFlow.update { it.copy(materialTitle = event.value) }
            is UploadMaterialUiEvent.OnCategoryChange ->
                _uiDataStateFlow.update { it.copy(category = event.value) }
            is UploadMaterialUiEvent.OnSuggestionPick ->
                _uiDataStateFlow.update { it.copy(category = event.category) }
            is UploadMaterialUiEvent.OnDescriptionChange ->
                _uiDataStateFlow.update { it.copy(description = event.value) }
            is UploadMaterialUiEvent.OnPriceChange ->
                _uiDataStateFlow.update { it.copy(price = event.value.filter { c -> c.isDigit() || c == '.' }) }
            is UploadMaterialUiEvent.OnFileSelected ->
                _uiDataStateFlow.update {
                    it.copy(selectedFileUri = event.uri, selectedFileName = event.fileName, errorMessage = null)
                }
            is UploadMaterialUiEvent.OnThumbnailSelected ->
                _uiDataStateFlow.update {
                    it.copy(thumbnailUri = event.uri, thumbnailName = event.fileName)
                }
            UploadMaterialUiEvent.OnUploadClick -> {
                val state = _uiDataStateFlow.value
                when {
                    state.materialTitle.isBlank() ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Title is required") }
                    state.category.isBlank() ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Category is required") }
                    state.description.isBlank() ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Description is required") }
                    state.selectedFileUri == null ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Please attach a file") }
                    state.category.length > 100 ->
                        _uiDataStateFlow.update { it.copy(errorMessage = "Category must be 100 characters or less") }
                    else -> coroutineScope.launch {
                        upload(context, state, navigate)
                    }
                }
            }
            UploadMaterialUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            UploadMaterialUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }

    private suspend fun upload(
        context: Context,
        state: UploadMaterialUiDataState,
        navigate: (NavigationAction) -> Unit
    ) {
        val fileUri = state.selectedFileUri ?: return
        _uiDataStateFlow.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val filePart = fileUri.asMultipartPart(context, "file")
            val thumbPart = state.thumbnailUri?.takeIf { it != Uri.EMPTY }?.asMultipartPart(context, "thumbnail")
            val priceVal = state.price.toDoubleOrNull() ?: 0.0
            apiRepository.uploadMaterial(
                title = state.materialTitle.trim(),
                description = state.description.trim(),
                category = state.category.trim(),
                price = priceVal,
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
