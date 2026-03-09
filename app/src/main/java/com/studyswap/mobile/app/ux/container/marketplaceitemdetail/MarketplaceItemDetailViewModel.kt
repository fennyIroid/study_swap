package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarketplaceItemDetailViewModel @Inject constructor(
    private val getMarketplaceItemDetailUiStateUseCase: GetMarketplaceItemDetailUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val itemId: String = savedStateHandle.get<String>("itemId") ?: ""

    val uiState: MarketplaceItemDetailUiState = getMarketplaceItemDetailUiStateUseCase(
        itemId = itemId,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
