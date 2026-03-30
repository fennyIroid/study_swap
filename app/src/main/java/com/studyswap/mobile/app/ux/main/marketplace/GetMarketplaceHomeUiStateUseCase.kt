package com.studyswap.mobile.app.ux.main.marketplace

import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.StudyMaterialDto
import com.studyswap.mobile.app.data.source.remote.model.toApiDouble
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMarketplaceHomeUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataStore: AppPreferenceDataStore
) {

    private val _uiDataStateFlow = MutableStateFlow(MarketplaceHomeUiDataState())

    operator fun invoke(
        coroutineScope: CoroutineScope
    ): MarketplaceHomeUiState = MarketplaceHomeUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(coroutineScope, event) }
    )

    private fun handleEvent(coroutineScope: CoroutineScope, event: MarketplaceHomeUiEvent) {
        when (event) {
            is MarketplaceHomeUiEvent.Load -> coroutineScope.launch {
                val user = dataStore.getUserData()
                val firstName = user?.fullName?.split(" ")?.firstOrNull().orEmpty()
                _uiDataStateFlow.update {
                    it.copy(userName = firstName.ifBlank { it.userName })
                }
                loadCategories()
                refreshMaterialLists()
            }
            is MarketplaceHomeUiEvent.RefreshMaterials -> coroutineScope.launch {
                refreshMaterialLists()
            }
            is MarketplaceHomeUiEvent.OnSearchChange -> {
                _uiDataStateFlow.update { it.copy(searchQuery = event.query) }
                coroutineScope.launch {
                    val q = event.query
                    delay(400)
                    if (_uiDataStateFlow.value.searchQuery == q) refreshMaterialLists()
                }
            }
            is MarketplaceHomeUiEvent.OnCategorySelected -> {
                _uiDataStateFlow.update { it.copy(selectedCategory = event.category) }
                coroutineScope.launch { refreshMaterialLists() }
            }
            else -> Unit
        }
    }

    private suspend fun loadCategories() {
        apiRepository.getCategories().collect { result ->
            when (result) {
                is NetworkResult.Success ->
                    _uiDataStateFlow.update {
                        it.copy(apiCategories = result.data?.data.orEmpty())
                    }
                else -> Unit
            }
        }
    }

    private suspend fun refreshMaterialLists() {
        val s = _uiDataStateFlow.value
        val search = s.searchQuery.trim().takeIf { it.isNotEmpty() }
        val category = s.selectedCategory
        _uiDataStateFlow.update { it.copy(isLoading = true, errorMessage = null) }

        var trendingItems: List<TrendingNoteItem> = emptyList()
        var freshItems: List<FreshMaterialItem> = emptyList()
        var hadError: String? = null

        apiRepository.getMaterials(search, category, "trending", 1).collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success ->
                    trendingItems = result.data?.data?.data.orEmpty().map { it.toTrendingItem() }
                is NetworkResult.Error -> hadError = result.message
                is NetworkResult.UnAuthenticated -> hadError = "Please sign in to browse materials."
            }
        }

        apiRepository.getMaterials(search, category, "newest", 1).collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success ->
                    freshItems = result.data?.data?.data.orEmpty().map { it.toFreshItem() }
                is NetworkResult.Error -> hadError = result.message ?: hadError
                is NetworkResult.UnAuthenticated -> Unit
            }
        }

        _uiDataStateFlow.update {
            it.copy(
                isLoading = false,
                trendingNotes = trendingItems,
                freshMaterials = freshItems,
                errorMessage = hadError
            )
        }
    }

    private fun StudyMaterialDto.toTrendingItem(): TrendingNoteItem {
        val sub = description?.lines()?.firstOrNull()?.take(80).orEmpty()
        return TrendingNoteItem(
            id = (id ?: 0).toString(),
            title = title.orEmpty().ifBlank { "Material" },
            subtitle = sub,
            rating = rating.toApiDouble(),
            price = price.toApiDouble(),
            tag = category ?: "NOTE",
            coverUrl = thumbnail
        )
    }

    private fun StudyMaterialDto.toFreshItem(): FreshMaterialItem {
        val sub = description?.lines()?.firstOrNull()?.take(80).orEmpty()
        return FreshMaterialItem(
            id = (id ?: 0).toString(),
            title = title.orEmpty().ifBlank { "Material" },
            subtitle = sub,
            rating = rating.toApiDouble(),
            price = price.toApiDouble(),
            tag = category ?: "NOTE",
            coverUrl = thumbnail
        )
    }
}
