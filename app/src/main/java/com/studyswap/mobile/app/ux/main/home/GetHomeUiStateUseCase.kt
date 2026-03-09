package com.studyswap.mobile.app.ux.main.home

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
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
            // ON_RESUME triggers this; keep spinner only if list is currently empty
            is HomeUiEvent.LoadGroups -> coroutineScope.launch {
                val silent = _uiDataStateFlow.value.allGroups.isNotEmpty()
                fetchGroups(silent = silent)
            }
            is HomeUiEvent.OnFilterChanged -> _uiDataStateFlow.update { it.copy(selectedFilter = event.filter) }
            is HomeUiEvent.OnDismissError -> _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }

    private suspend fun fetchGroups(silent: Boolean = false) {
        val myUserId = dataStore.getUserData()?.id

        apiRepository.getGroups().collect { result ->
            when (result) {
                is NetworkResult.Loading -> if (!silent) _uiDataStateFlow.update { it.copy(isLoading = true) }
                is NetworkResult.Success -> {
                    val allGroups = result.data?.data ?: emptyList()
                    val myGroups = if (myUserId != null) {
                        allGroups.filter { it.creatorId == myUserId }
                    } else emptyList()
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, allGroups = allGroups, myGroups = myGroups)
                    }
                }
                is NetworkResult.Error -> _uiDataStateFlow.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                is NetworkResult.UnAuthenticated -> _uiDataStateFlow.update { it.copy(isLoading = false) }
            }
        }
    }
}
