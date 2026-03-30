package com.studyswap.mobile.app.ux.container.uploadgroupfile

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

data class UploadGroupFileUiDataState(
    val title: String = "",
    val category: String = "",
    val selectedFileUri: Uri? = null,
    val selectedFileName: String? = null,
    val thumbnailUri: Uri? = null,
    val thumbnailName: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class UploadGroupFileUiEvent {
    data class OnTitleChange(val value: String) : UploadGroupFileUiEvent()
    data class OnCategoryChange(val value: String) : UploadGroupFileUiEvent()
    data class OnFileSelected(val uri: Uri?, val fileName: String?) : UploadGroupFileUiEvent()
    data class OnThumbnailSelected(val uri: Uri?, val fileName: String?) : UploadGroupFileUiEvent()
    object OnUploadClick : UploadGroupFileUiEvent()
    object OnBackClick : UploadGroupFileUiEvent()
    object OnDismissError : UploadGroupFileUiEvent()
}

data class UploadGroupFileUiState(
    val uiDataStateFlow: StateFlow<UploadGroupFileUiDataState>,
    val event: (UploadGroupFileUiEvent) -> Unit
)
