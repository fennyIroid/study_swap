package com.studyswap.mobile.app.ux.container.uploadmaterial

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

data class UploadMaterialUiDataState(
    val materialTitle: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val suggestedCategories: List<String> = emptyList(),
    val selectedFileUri: Uri? = null,
    val selectedFileName: String? = null,
    val thumbnailUri: Uri? = null,
    val thumbnailName: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class UploadMaterialUiEvent {
    object Load : UploadMaterialUiEvent()
    data class OnMaterialTitleChange(val value: String) : UploadMaterialUiEvent()
    data class OnCategoryChange(val value: String) : UploadMaterialUiEvent()
    data class OnSuggestionPick(val category: String) : UploadMaterialUiEvent()
    data class OnDescriptionChange(val value: String) : UploadMaterialUiEvent()
    data class OnPriceChange(val value: String) : UploadMaterialUiEvent()
    data class OnFileSelected(val uri: Uri?, val fileName: String?) : UploadMaterialUiEvent()
    data class OnThumbnailSelected(val uri: Uri?, val fileName: String?) : UploadMaterialUiEvent()
    object OnUploadClick : UploadMaterialUiEvent()
    object OnBackClick : UploadMaterialUiEvent()
    object OnDismissError : UploadMaterialUiEvent()
}

data class UploadMaterialUiState(
    val uiDataStateFlow: StateFlow<UploadMaterialUiDataState>,
    val event: (UploadMaterialUiEvent) -> Unit
)
