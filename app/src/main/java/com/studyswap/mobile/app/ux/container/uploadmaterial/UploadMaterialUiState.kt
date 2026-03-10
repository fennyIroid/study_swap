package com.studyswap.mobile.app.ux.container.uploadmaterial

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

data class UploadMaterialUiDataState(
    val materialTitle: String = "",
    val selectedCategory: String = "",
    val subjectCode: String = "",
    val selectedSemester: String = "",
    val selectedFileUri: Uri? = null,
    val selectedFileName: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class UploadMaterialUiEvent {
    data class OnMaterialTitleChange(val value: String) : UploadMaterialUiEvent()
    data class OnCategorySelect(val category: String) : UploadMaterialUiEvent()
    data class OnSubjectCodeChange(val value: String) : UploadMaterialUiEvent()
    data class OnSemesterSelect(val semester: String) : UploadMaterialUiEvent()
    data class OnFileSelected(val uri: Uri?, val fileName: String?) : UploadMaterialUiEvent()
    object OnUploadClick : UploadMaterialUiEvent()
    object OnBackClick : UploadMaterialUiEvent()
    object OnDismissError : UploadMaterialUiEvent()
}

data class UploadMaterialUiState(
    val uiDataStateFlow: StateFlow<UploadMaterialUiDataState>,
    val event: (UploadMaterialUiEvent) -> Unit
)
