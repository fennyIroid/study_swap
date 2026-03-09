package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import kotlinx.coroutines.flow.StateFlow

enum class MarketplaceItemFormat { NOTES, PDF }

data class MarketplaceItemDetail(
    val id: String,
    val title: String,
    val authorName: String,
    val authorSubtitle: String,
    val rating: Double,
    val ratingCount: Int,
    val semesterLabel: String,
    val description: String,
    val formats: List<MarketplaceItemFormat>,
    val coverUrl: String? = null,
    val fileName: String,
    val fileMetaLine: String
)

data class MarketplaceItemDetailUiDataState(
    val item: MarketplaceItemDetail? = null,
    val isBookmarked: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class MarketplaceItemDetailUiEvent {
    object Load : MarketplaceItemDetailUiEvent()
    object OnBackClick : MarketplaceItemDetailUiEvent()
    object OnBookmarkToggle : MarketplaceItemDetailUiEvent()
    object OnCameraClick : MarketplaceItemDetailUiEvent()
    object OnDownloadClick : MarketplaceItemDetailUiEvent()
    object OnFileMoreClick : MarketplaceItemDetailUiEvent()
    object OnDismissError : MarketplaceItemDetailUiEvent()
}

data class MarketplaceItemDetailUiState(
    val uiDataStateFlow: StateFlow<MarketplaceItemDetailUiDataState>,
    val event: (MarketplaceItemDetailUiEvent) -> Unit
)
