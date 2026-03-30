package com.studyswap.mobile.app.ux.main.marketplace

import kotlinx.coroutines.flow.StateFlow

data class TrendingNoteItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val rating: Double,
    val price: Double,
    val tag: String,
    val coverUrl: String? = null
)

data class FreshMaterialItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val rating: Double,
    val price: Double,
    val tag: String,
    val coverUrl: String? = null
)

data class MarketplaceHomeUiDataState(
    val userName: String = "",
    val greetingEmoji: String = "👋",
    val searchQuery: String = "",
    /** `null` means no category filter (all). */
    val selectedCategory: String? = null,
    val apiCategories: List<String> = emptyList(),
    val trendingNotes: List<TrendingNoteItem> = emptyList(),
    val freshMaterials: List<FreshMaterialItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class MarketplaceHomeUiEvent {
    object Load : MarketplaceHomeUiEvent()
    object RefreshMaterials : MarketplaceHomeUiEvent()
    data class OnSearchChange(val query: String) : MarketplaceHomeUiEvent()
    /** Pass `null` for “All categories”. */
    data class OnCategorySelected(val category: String?) : MarketplaceHomeUiEvent()
    object OnTrendingSeeAll : MarketplaceHomeUiEvent()
    object OnFreshSeeAll : MarketplaceHomeUiEvent()
    data class OnTrendingClick(val id: String) : MarketplaceHomeUiEvent()
    data class OnFreshClick(val id: String) : MarketplaceHomeUiEvent()
    object OnNotificationsClick : MarketplaceHomeUiEvent()
}

data class MarketplaceHomeUiState(
    val uiDataStateFlow: StateFlow<MarketplaceHomeUiDataState>,
    val event: (MarketplaceHomeUiEvent) -> Unit
)
