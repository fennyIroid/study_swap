package com.studyswap.mobile.app.ux.container.mymaterials

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.MaterialAccessEntryDto
import com.studyswap.mobile.app.data.source.remote.model.MyMaterialDashboardRowDto
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMyMaterialsUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    private val _uiDataStateFlow = MutableStateFlow(MyMaterialsUiDataState())

    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): MyMaterialsUiState = MyMaterialsUiState(
        uiDataStateFlow = _uiDataStateFlow,
        event = { event -> handleEvent(event, coroutineScope, navigate) }
    )

    private fun handleEvent(
        event: MyMaterialsUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            MyMaterialsUiEvent.Load -> coroutineScope.launch { fetchRows() }
            is MyMaterialsUiEvent.OnToggleExpand -> {
                val id = event.materialId
                val prev = _uiDataStateFlow.value
                val wasExpanded = id in prev.expandedIds
                val willExpand = !wasExpanded
                _uiDataStateFlow.update { s ->
                    val next = if (wasExpanded) s.expandedIds - id else s.expandedIds + id
                    s.copy(expandedIds = next)
                }
                if (willExpand) coroutineScope.launch { refreshAccessFor(id) }
            }
            MyMaterialsUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
            MyMaterialsUiEvent.OnDismissError -> _uiDataStateFlow.update { it.copy(errorMessage = null) }
        }
    }

    private suspend fun fetchRows() {
        _uiDataStateFlow.update { it.copy(isLoading = true, errorMessage = null) }
        apiRepository.myMaterials().collect { result ->
            when (result) {
                is NetworkResult.Loading -> Unit
                is NetworkResult.Success -> {
                    val mapped = result.data?.data.orEmpty().mapNotNull { it.toRow() }
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, rows = mapped, errorMessage = null)
                    }
                }
                is NetworkResult.Error -> {
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _uiDataStateFlow.update {
                        it.copy(isLoading = false, errorMessage = "Please sign in.")
                    }
                }
            }
        }
    }

    private suspend fun refreshAccessFor(materialId: Int) {
        apiRepository.getMaterialAccess(materialId).collect { result ->
            if (result !is NetworkResult.Success) return@collect
            val list = result.data?.data.orEmpty().map { it.toAccessRow() }
            _uiDataStateFlow.update { s ->
                s.copy(
                    rows = s.rows.map { row ->
                        if (row.materialId == materialId) row.copy(accessList = list) else row
                    }
                )
            }
        }
    }

    private fun MyMaterialDashboardRowDto.toRow(): MyMaterialRowUi? {
        val m = material ?: return null
        val id = m.id ?: return null
        return MyMaterialRowUi(
            materialId = id,
            title = m.title.orEmpty().ifBlank { "Material" },
            category = m.category,
            thumbnail = m.thumbnail,
            purchaseCount = purchaseCount ?: 0,
            accessList = accessList.orEmpty().map { it.toAccessRow() }
        )
    }

    private fun MaterialAccessEntryDto.toAccessRow(): MaterialAccessRowUi {
        return MaterialAccessRowUi(
            userName = user?.fullName.orEmpty().ifBlank { "User ${userId ?: ""}" },
            email = user?.email,
            grantedAt = grantedAt.orEmpty()
        )
    }
}
