package com.studyswap.mobile.app.ux.main.home

import kotlinx.coroutines.flow.StateFlow

data class HomeUiDataState(
    val userName: String = "Alex",
    val isLoading: Boolean = false,
    val recentMaterials: List<RecentMaterial> = emptyList(),
    val trendingProducts: List<TrendingProduct> = emptyList(),
    val errorMessage: String? = null
)

data class RecentMaterial(
    val id: Int,
    val title: String,
    val department: String,
    val pageCount: Int,
    val progress: Float // 0f to 1f
)

data class TrendingProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val rating: Double,
    val imageUrl: String? = null
)

sealed class HomeUiEvent {
    object LoadHomeData : HomeUiEvent()
    object OnUploadClick : HomeUiEvent()
    object OnFindMaterialsClick : HomeUiEvent()
    data class OnMaterialClick(val id: Int) : HomeUiEvent()
    data class OnProductClick(val id: Int) : HomeUiEvent()
}

data class HomeUiState(
    val uiDataStateFlow: StateFlow<HomeUiDataState>,
    val event: (HomeUiEvent) -> Unit
)
