package com.studyswap.mobile.app.ux.main.marketplace

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GetMarketplaceHomeUiStateUseCase @Inject constructor() {

    private val _uiDataStateFlow = MutableStateFlow(MarketplaceHomeUiDataState())

    operator fun invoke(
        coroutineScope: CoroutineScope
    ): MarketplaceHomeUiState = MarketplaceHomeUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(event) }
    )

    private fun handleEvent(event: MarketplaceHomeUiEvent) {
        when (event) {
            is MarketplaceHomeUiEvent.Load -> {
                if (_uiDataStateFlow.value.trendingNotes.isNotEmpty()) return
                _uiDataStateFlow.update {
                    it.copy(
                        trendingNotes = listOf(
                            TrendingNoteItem(
                                id = "t1",
                                title = "Advanced Calculus",
                                subtitle = "Finals Prep 2024",
                                rating = 4.9,
                                price = 5.0,
                                tag = "CALC"
                            ),
                            TrendingNoteItem(
                                id = "t2",
                                title = "Corporate Finance",
                                subtitle = "Case Study Pack",
                                rating = 4.7,
                                price = 6.5,
                                tag = "FIN"
                            )
                        ),
                        freshMaterials = listOf(
                            FreshMaterialItem(
                                id = "f1",
                                title = "Intro to Algorithms",
                                subtitle = "Data Structures",
                                rating = 4.6,
                                price = 12.0,
                                tag = "CS112"
                            ),
                            FreshMaterialItem(
                                id = "f2",
                                title = "Organic Chemistry",
                                subtitle = "Midterm Prep",
                                rating = 4.8,
                                price = 12.0,
                                tag = "CHEM201"
                            ),
                            FreshMaterialItem(
                                id = "f3",
                                title = "Modern Art History",
                                subtitle = "Summaries",
                                rating = 5.0,
                                price = 3.5,
                                tag = "ART100"
                            ),
                            FreshMaterialItem(
                                id = "f4",
                                title = "Corporate Finance",
                                subtitle = "Case Studies",
                                rating = 5.0,
                                price = 3.99,
                                tag = "MKT201"
                            )
                        )
                    )
                }
            }
            is MarketplaceHomeUiEvent.OnSearchChange -> _uiDataStateFlow.update { it.copy(searchQuery = event.query) }
            is MarketplaceHomeUiEvent.OnCategorySelected -> _uiDataStateFlow.update { it.copy(selectedCategory = event.category) }
            else -> Unit
        }
    }
}
