package com.studyswap.mobile.app.ux.main.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarketplaceHomeViewModel @Inject constructor(
    private val getMarketplaceHomeUiStateUseCase: GetMarketplaceHomeUiStateUseCase
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    val uiState: MarketplaceHomeUiState = getMarketplaceHomeUiStateUseCase(viewModelScope)
}
