package com.studyswap.mobile.app.ux.startup.splash

import androidx.lifecycle.ViewModel
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.ux.startup.welcome.WelcomeRoute
import com.studyswap.mobile.app.ux.sample.BaseRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appPreferenceDataStore: AppPreferenceDataStore
) : ViewModel() {

    private val _nextRoute = MutableStateFlow<String?>(null)
    val nextRoute: StateFlow<String?> = _nextRoute.asStateFlow()

    init {
        runBlocking {
            val token = appPreferenceDataStore.getUserToken()
            _nextRoute.value = if (token.isNullOrBlank()) {
                WelcomeRoute.routeDefinition.value
            } else {
                BaseRoute.routeDefinition.value
            }
        }
    }
}
