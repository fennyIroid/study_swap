package com.studyswap.mobile.app.ux.sample

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Sample template: UI state and events for a feature.
 * Copy and rename (e.g. HomeUiState, HomeUiDataState, HomeUiEvent).
 */
data class BaseUiDataState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class BaseUiState(
    val baseUiStateFlow: StateFlow<BaseUiDataState>,
    val event: (BaseUiEvent) -> Unit
)

sealed interface BaseUiEvent {
    data object OnSubmitClick : BaseUiEvent
}
