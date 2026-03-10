package com.studyswap.mobile.app.ux.container.uploadmaterial

import com.studyswap.mobile.app.navigation.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUploadMaterialUiStateUseCase @Inject constructor() {

    private val _uiDataStateFlow = MutableStateFlow(UploadMaterialUiDataState())

    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): UploadMaterialUiState = UploadMaterialUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        event: UploadMaterialUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is UploadMaterialUiEvent.OnMaterialTitleChange ->
                _uiDataStateFlow.update { it.copy(materialTitle = event.value) }
            is UploadMaterialUiEvent.OnCategorySelect ->
                _uiDataStateFlow.update { it.copy(selectedCategory = event.category) }
            is UploadMaterialUiEvent.OnSubjectCodeChange ->
                _uiDataStateFlow.update { it.copy(subjectCode = event.value) }
            is UploadMaterialUiEvent.OnSemesterSelect ->
                _uiDataStateFlow.update { it.copy(selectedSemester = event.semester) }
            is UploadMaterialUiEvent.OnFileSelected ->
                _uiDataStateFlow.update {
                    it.copy(selectedFileUri = event.uri, selectedFileName = event.fileName)
                }
            UploadMaterialUiEvent.OnUploadClick -> {
                val state = _uiDataStateFlow.value
                if (state.materialTitle.isBlank()) {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Material title is required") }
                    return
                }
                if (state.selectedFileUri == null) {
                    _uiDataStateFlow.update { it.copy(errorMessage = "Please select a file to upload") }
                    return
                }
                coroutineScope.launch {
                    _uiDataStateFlow.update { it.copy(isLoading = true) }
                    // TODO: call API uploadMaterial(...)
                    kotlinx.coroutines.delay(800)
                    _uiDataStateFlow.update { it.copy(isLoading = false) }
                    navigate(NavigationAction.Pop())
                }
            }
            UploadMaterialUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            UploadMaterialUiEvent.OnDismissError ->
                _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }
}
