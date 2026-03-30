package com.studyswap.mobile.app.ux.container.profile

import android.content.Context
import android.net.Uri
import com.studyswap.mobile.app.data.source.local.datastore.AppPreferenceDataStore
import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.repository.ApiRepository
import com.studyswap.mobile.app.navigation.NavigationAction
import com.studyswap.mobile.app.utils.asMultipartPart
import com.studyswap.mobile.app.ux.container.mymaterials.MyMaterialsRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetProfileUiStateUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataStore: AppPreferenceDataStore
) {

    private val _profileUiDataStateFlow = MutableStateFlow(ProfileUiDataState())

    operator fun invoke(
        context: Context,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ): ProfileUiState {
        // Fetch user info when the use case is first invoked (screen open)
        fetchUserInfo(coroutineScope)
        
        return ProfileUiState(
            profileUiDataStateFlow = _profileUiDataStateFlow,
            event = { event -> handleEvent(context, event, coroutineScope, navigate) }
        )
    }

    private fun fetchUserInfo(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            // 1. Load from session (DataStore) first for immediate display
            val cachedUser = dataStore.getUserData()
            if (cachedUser != null) {
                _profileUiDataStateFlow.update { it.copy(userData = cachedUser) }
            }

            // 2. Fetch fresh data for the authenticated user (token only; no user_id in body)
            apiRepository.getUserInfo().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        if (cachedUser == null) _profileUiDataStateFlow.update { it.copy(isLoading = true) }
                    }
                    is NetworkResult.Success -> {
                        val freshUser = result.data?.userData
                        if (freshUser != null) {
                            dataStore.saveUserData(freshUser)
                            _profileUiDataStateFlow.update { 
                                it.copy(isLoading = false, userData = freshUser) 
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        _profileUiDataStateFlow.update { 
                            it.copy(isLoading = false, errorMessage = result.message) 
                        }
                    }
                    is NetworkResult.UnAuthenticated -> {
                        _profileUiDataStateFlow.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun handleEvent(
        context: Context,
        event: ProfileUiEvent,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit
    ) {
        when (event) {
            is ProfileUiEvent.OnProfilePhotoPicked -> coroutineScope.launch {
                uploadProfilePhoto(context, event.uri)
            }
            is ProfileUiEvent.OnEditProfileClick -> { /* TODO */ }
            is ProfileUiEvent.OnUploadedMaterialsClick -> {
                navigate(NavigationAction.Navigate(MyMaterialsRoute.createRoute()))
            }
            is ProfileUiEvent.OnSavedMaterialsClick -> { /* TODO */ }
            is ProfileUiEvent.OnHelpSupportClick -> { /* TODO */ }
            is ProfileUiEvent.OnSettingsClick -> { /* TODO */ }
            is ProfileUiEvent.OnBackClick -> navigate(NavigationAction.Pop())
        }
    }

    private suspend fun uploadProfilePhoto(context: Context, uri: Uri) {
        _profileUiDataStateFlow.update {
            it.copy(isUploadingPhoto = true, errorMessage = null)
        }
        try {
            val part = uri.asMultipartPart(context, "photo")
            apiRepository.updateProfilePhoto(part).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> Unit
                    is NetworkResult.Success -> {
                        val fresh = result.data?.userData
                        if (fresh != null) {
                            dataStore.saveUserData(fresh)
                            _profileUiDataStateFlow.update {
                                it.copy(userData = fresh, isUploadingPhoto = false)
                            }
                        } else {
                            refreshUserAfterPhotoUpload()
                        }
                    }
                    is NetworkResult.Error -> {
                        _profileUiDataStateFlow.update {
                            it.copy(isUploadingPhoto = false, errorMessage = result.message)
                        }
                    }
                    is NetworkResult.UnAuthenticated -> {
                        _profileUiDataStateFlow.update {
                            it.copy(isUploadingPhoto = false, errorMessage = "Please sign in again.")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _profileUiDataStateFlow.update {
                it.copy(
                    isUploadingPhoto = false,
                    errorMessage = e.message ?: "Could not update photo"
                )
            }
        }
    }

    private suspend fun refreshUserAfterPhotoUpload() {
        apiRepository.getUserInfo().collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    val fresh = result.data?.userData
                    if (fresh != null) {
                        dataStore.saveUserData(fresh)
                        _profileUiDataStateFlow.update {
                            it.copy(userData = fresh, isUploadingPhoto = false)
                        }
                    } else {
                        _profileUiDataStateFlow.update { it.copy(isUploadingPhoto = false) }
                    }
                }
                is NetworkResult.Error -> {
                    _profileUiDataStateFlow.update {
                        it.copy(isUploadingPhoto = false, errorMessage = result.message)
                    }
                }
                is NetworkResult.UnAuthenticated -> {
                    _profileUiDataStateFlow.update { it.copy(isUploadingPhoto = false) }
                }
                else -> Unit
            }
        }
    }
}
