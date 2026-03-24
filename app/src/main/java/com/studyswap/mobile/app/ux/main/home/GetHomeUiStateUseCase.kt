package com.studyswap.mobile.app.ux.main.home

import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
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
            else -> { /* Handle others */ }
        }
    }

    private suspend fun loadInitialData() {
        _uiDataStateFlow.update { it.copy(isLoading = true) }
        
        val user = dataStore.getUserData()
        
        // Mocking some data for the new UI elements for now
        val recent = listOf(
            RecentMaterial(1, "Quantum Mechanics Basics", "Physics Department", 24, 0.7f),
            RecentMaterial(2, "Organic Chemistry II", "Science Faculty", 12, 0.4f)
        )
        
        val trending = listOf(
            TrendingProduct(1, "Microbiology Finals Prep", 12.50, 4.9),
            TrendingProduct(2, "History of Modern Art", 8.00, 4.7)
        )

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
