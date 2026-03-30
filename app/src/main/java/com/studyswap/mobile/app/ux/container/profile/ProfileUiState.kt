package com.studyswap.mobile.app.ux.container.profile

import com.studyswap.mobile.app.data.source.remote.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileUiState(
    val profileUiDataStateFlow: StateFlow<ProfileUiDataState> = MutableStateFlow(ProfileUiDataState()),
    val event: (ProfileUiEvent) -> Unit = {}
)

data class ProfileUiDataState(
    val userData: UserData? = null,
    val isLoading: Boolean = false,
    val isUploadingPhoto: Boolean = false,
    val errorMessage: String? = null
)

sealed interface ProfileUiEvent {
    data class OnProfilePhotoPicked(val uri: android.net.Uri) : ProfileUiEvent
    data object OnEditProfileClick : ProfileUiEvent
    data object OnUploadedMaterialsClick : ProfileUiEvent
    data object OnSavedMaterialsClick : ProfileUiEvent
    data object OnHelpSupportClick : ProfileUiEvent
    data object OnSettingsClick : ProfileUiEvent
    data object OnBackClick : ProfileUiEvent
}
