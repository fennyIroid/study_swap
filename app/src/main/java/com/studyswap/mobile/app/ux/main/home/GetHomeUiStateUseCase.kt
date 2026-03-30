package com.studyswap.mobile.app.ux.main.home

import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.toApiDouble
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetHomeUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataStore: AppPreferenceDataStore
) {

    private val _uiDataStateFlow = MutableStateFlow(HomeUiDataState())

    operator fun invoke(
        coroutineScope: CoroutineScope
    ): HomeUiState = HomeUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(event, coroutineScope) }
    )

    private fun handleEvent(event: HomeUiEvent, coroutineScope: CoroutineScope) {
        when (event) {
            is HomeUiEvent.LoadHomeData -> coroutineScope.launch {
                loadInitialData()
            }
            else -> Unit
        }
    }

    private suspend fun loadInitialData() {
        _uiDataStateFlow.update { it.copy(isLoading = true) }

        val user = dataStore.getUserData()

        val recent = listOf(
            RecentMaterial(1, "Quantum Mechanics Basics", "Physics Department", 24, 0.7f),
            RecentMaterial(2, "Organic Chemistry II", "Science Faculty", 12, 0.4f)
        )

        var trending: List<TrendingProduct> = _uiDataStateFlow.value.trendingProducts
        apiRepository.getMaterials(null, null, "trending", 1).collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success -> {
                    val items = result.data?.data?.data.orEmpty().take(4).mapNotNull { dto ->
                        val id = dto.id ?: return@mapNotNull null
                        TrendingProduct(
                            id = id,
                            title = dto.title.orEmpty().ifBlank { "Material" },
                            price = dto.price.toApiDouble(),
                            rating = dto.rating.toApiDouble(),
                            imageUrl = dto.thumbnail
                        )
                    }
                    if (items.isNotEmpty()) trending = items
                }
                is NetworkResult.Error -> Unit
                is NetworkResult.UnAuthenticated -> Unit
            }
        }

        _uiDataStateFlow.update {
            it.copy(
                isLoading = false,
                userName = user?.fullName?.split(" ")?.firstOrNull() ?: "Alex",
                recentMaterials = recent,
                trendingProducts = trending
            )
        }
    }
}
