package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import kotlinx.coroutines.flow.StateFlow

enum class MarketplaceItemFormat { NOTES, PDF }

data class MarketplaceItemDetail(
    val id: String,
    val numericId: Int,
    val title: String,
    val authorName: String,
    val authorSubtitle: String,
    val rating: Double,
    val ratingCount: Int,
    val semesterLabel: String,
    val description: String,
    val formats: List<MarketplaceItemFormat>,
    val coverUrl: String? = null,
    val fileUrl: String? = null,
    val fileName: String,
    val fileMetaLine: String,
    val ownerUserId: Int? = null,
    val authorEmail: String? = null,
    val authorPhone: String? = null,
    val hasAccessFromApi: Boolean = false
)

data class MarketplaceItemDetailUiDataState(
    val item: MarketplaceItemDetail? = null,
    val currentUserId: Int? = null,
    val isBookmarked: Boolean = false,
    val isLoading: Boolean = false,
    val isRatingLoading: Boolean = false,
    val isDeleteLoading: Boolean = false,
    val showDeleteConfirm: Boolean = false,
    val hasRedeemedUnlock: Boolean = false,
    val authorOtpCode: String? = null,
    val authorOtpDeadlineWallMs: Long? = null,
    val isGeneratingOtp: Boolean = false,
    val buyerOtpInput: String = "",
    val isRedeeming: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null
) {
    val isOwner: Boolean
        get() = item?.ownerUserId != null && item.ownerUserId == currentUserId

    val fileAccessUnlocked: Boolean
        get() = isOwner || hasRedeemedUnlock || (item?.hasAccessFromApi == true)
}

sealed class MarketplaceItemDetailUiEvent {
    object Load : MarketplaceItemDetailUiEvent()
    object OnBackClick : MarketplaceItemDetailUiEvent()
    object OnBookmarkToggle : MarketplaceItemDetailUiEvent()
    object OnCameraClick : MarketplaceItemDetailUiEvent()
    object OnDownloadClick : MarketplaceItemDetailUiEvent()
    object OnFileMoreClick : MarketplaceItemDetailUiEvent()
    object OnDismissError : MarketplaceItemDetailUiEvent()
    object OnDismissInfo : MarketplaceItemDetailUiEvent()
    data class OnRateMaterial(val stars: Int) : MarketplaceItemDetailUiEvent()
    object OnDeleteClick : MarketplaceItemDetailUiEvent()
    object OnDismissDeleteConfirm : MarketplaceItemDetailUiEvent()
    object OnConfirmDelete : MarketplaceItemDetailUiEvent()
    object OnGenerateOtp : MarketplaceItemDetailUiEvent()
    data class OnBuyerOtpChange(val value: String) : MarketplaceItemDetailUiEvent()
    object OnRedeemOtp : MarketplaceItemDetailUiEvent()
}

data class MarketplaceItemDetailUiState(
    val uiDataStateFlow: StateFlow<MarketplaceItemDetailUiDataState>,
    val event: (MarketplaceItemDetailUiEvent) -> Unit
)
