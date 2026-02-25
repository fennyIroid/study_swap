package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    // Add methods returning Flow<NetworkResult<T>> as needed
    // Example: suspend fun login(req: LoginRequest): Flow<NetworkResult<AuthResponse>>
}
