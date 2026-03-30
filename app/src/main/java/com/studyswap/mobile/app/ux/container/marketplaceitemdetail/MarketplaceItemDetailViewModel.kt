package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MarketplaceItemDetailViewModel @Inject constructor(
    private val getMarketplaceItemDetailUiStateUseCase: GetMarketplaceItemDetailUiStateUseCase,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    private val itemId: String = savedStateHandle.get<String>("itemId") ?: ""

    val uiState: MarketplaceItemDetailUiState = getMarketplaceItemDetailUiStateUseCase(
        itemId = itemId,
        context = context,
        coroutineScope = viewModelScope
    ) { navigate(it) }
}
