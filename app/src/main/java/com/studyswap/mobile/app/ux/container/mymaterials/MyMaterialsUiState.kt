package com.studyswap.mobile.app.ux.container.mymaterials

import kotlinx.coroutines.flow.StateFlow

data class MaterialAccessRowUi(
    val userName: String,
    val email: String?,
    val grantedAt: String
)

data class MyMaterialRowUi(
    val materialId: Int,
    val title: String,
    val category: String?,
    val thumbnail: String?,
    val purchaseCount: Int,
    val accessList: List<MaterialAccessRowUi>
)

data class MyMaterialsUiDataState(
    val rows: List<MyMaterialRowUi> = emptyList(),
    val expandedIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class MyMaterialsUiEvent {
    object Load : MyMaterialsUiEvent()
    data class OnToggleExpand(val materialId: Int) : MyMaterialsUiEvent()
    object OnBackClick : MyMaterialsUiEvent()
    object OnDismissError : MyMaterialsUiEvent()
}

data class MyMaterialsUiState(
    val uiDataStateFlow: StateFlow<MyMaterialsUiDataState>,
    val event: (MyMaterialsUiEvent) -> Unit
)
