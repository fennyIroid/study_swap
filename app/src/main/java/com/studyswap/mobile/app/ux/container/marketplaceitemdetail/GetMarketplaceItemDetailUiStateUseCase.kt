package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import com.studyswap.mobile.app.navigation.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GetMarketplaceItemDetailUiStateUseCase @Inject constructor() {

    private val _uiDataStateFlow = MutableStateFlow(MarketplaceItemDetailUiDataState())

    operator fun invoke(
        itemId: String,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): MarketplaceItemDetailUiState = MarketplaceItemDetailUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(itemId, event, navigate) }
    )

    private fun handleEvent(
        itemId: String,
        event: MarketplaceItemDetailUiEvent,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            MarketplaceItemDetailUiEvent.Load -> {
                if (_uiDataStateFlow.value.item != null) return
                _uiDataStateFlow.update {
                    it.copy(
                        item = buildSample(itemId),
                        isLoading = false
                    )
                }
            }
            MarketplaceItemDetailUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            MarketplaceItemDetailUiEvent.OnBookmarkToggle -> _uiDataStateFlow.update { it.copy(isBookmarked = !it.isBookmarked) }
            MarketplaceItemDetailUiEvent.OnDismissError -> _uiDataStateFlow.update { it.copy(errorMessage = null) }
            MarketplaceItemDetailUiEvent.OnCameraClick -> Unit
            MarketplaceItemDetailUiEvent.OnDownloadClick -> Unit
            MarketplaceItemDetailUiEvent.OnFileMoreClick -> Unit
        }
    }

    private fun buildSample(itemId: String): MarketplaceItemDetail {
        val title = when (itemId) {
            "t1" -> "Advanced Calculus"
            "f1" -> "Intro to Algorithms"
            else -> "Intro to Macroeconomics Notes"
        }
        return MarketplaceItemDetail(
            id = itemId,
            title = title,
            authorName = "Sarah J.",
            authorSubtitle = "AUTHOR",
            rating = 4.8,
            ratingCount = 120,
            semesterLabel = "Spring '23",
            description = "Comprehensive notes covering chapters 1–5 of the standard curriculum.\n\nThese notes include detailed graphs for supply and demand and clear worked examples.",
            formats = listOf(MarketplaceItemFormat.NOTES, MarketplaceItemFormat.PDF),
            coverUrl = null,
            fileName = "Macro_Intro_Final.pdf",
            fileMetaLine = "2.4 MB • 14 PAGES • HIGH RES"
        )
    }
}
