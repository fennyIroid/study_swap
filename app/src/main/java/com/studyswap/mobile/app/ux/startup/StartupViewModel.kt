package com.studyswap.mobile.app.ux.startup

import androidx.lifecycle.ViewModel
import com.studyswap.mobile.app.navigation.ViewModelNav
import com.studyswap.mobile.app.navigation.ViewModelNavImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor() : ViewModel(), ViewModelNav by ViewModelNavImpl()
