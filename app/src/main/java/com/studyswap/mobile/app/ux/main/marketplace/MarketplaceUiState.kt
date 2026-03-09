package com.studyswap.mobile.app.ux.main.marketplace

import kotlinx.coroutines.flow.StateFlow

enum class MarketplaceCategory { COMPUTER_SCIENCE, MATH, HISTORY }

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
    val userName: String = "Alex Student",
    val greetingEmoji: String = "👋",
    val searchQuery: String = "",
    val selectedCategory: MarketplaceCategory = MarketplaceCategory.COMPUTER_SCIENCE,
    val trendingNotes: List<TrendingNoteItem> = emptyList(),
    val freshMaterials: List<FreshMaterialItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class MarketplaceHomeUiEvent {
    object Load : MarketplaceHomeUiEvent()
    data class OnSearchChange(val query: String) : MarketplaceHomeUiEvent()
    data class OnCategorySelected(val category: MarketplaceCategory) : MarketplaceHomeUiEvent()
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
