package com.studyswap.mobile.app.ux.startup.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import com.studyswap.mobile.app.ux.sample.BaseRoute
import com.studyswap.mobile.app.ux.startup.welcome.WelcomeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appPreferenceDataStore: AppPreferenceDataStore
) : ViewModel(), ViewModelNav by ViewModelNavImpl() {

    fun onLoginSuccess() {
        viewModelScope.launch {
            appPreferenceDataStore.setHasCompletedAuth(true)
            navigate(
                NavigationAction.NavigateWithPopUpTo(
                    route = BaseRoute.createRoute(),
                    popUpToRouteDefinition = WelcomeRoute.routeDefinition,
                    inclusive = true
                )
            )
        }
    }
}
