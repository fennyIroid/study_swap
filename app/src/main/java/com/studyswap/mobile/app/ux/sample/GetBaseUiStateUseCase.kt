package com.studyswap.mobile.app.ux.sample

import android.content.Context
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.navigation.NavRoute
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Sample template: UseCase that holds screen state and handles events.
 * Copy and rename (e.g. GetHomeUiStateUseCase), inject ApiRepository and call APIs in handleEvent.
 */
class GetBaseUiStateUseCase @Inject constructor() {

    private val _state = MutableStateFlow(BaseUiDataState())
    val state: StateFlow<BaseUiDataState> = _state.asStateFlow()

    private fun update(block: (BaseUiDataState) -> BaseUiDataState) {
        _state.update(block)
    }

    operator fun invoke(
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): BaseUiState = BaseUiState(
        baseUiStateFlow = state,
        event = { handleEvent(context, it, coroutineScope, navigate) }
    )

    private fun handleEvent(
        context: Context,
        event: BaseUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is BaseUiEvent.OnSubmitClick -> {
                // Example: coroutineScope.launch { apiRepository.someApi().collect { ... } }
                AppUtils.showSuccessMessage(context, "Sample action")
            }
        }
    }
}
