package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.*
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun signup(request: SignupRequest): Flow<NetworkResult<SignupResponse>>
    suspend fun login(request: LoginRequest): Flow<NetworkResult<LoginResponse>>
    suspend fun getUserInfo(request: UserInfoRequest): Flow<NetworkResult<UserInfoResponse>>
    
    suspend fun createGroup(
        name: String,
        description: String,
        groupType: String,
        subject: String,
        maxMembers: Int,
        isPublic: Int,
        approvalRequired: Int,
        groupIcon: java.io.File?
    ): Flow<NetworkResult<CreateGroupResponse>>

    suspend fun getGroups(): Flow<NetworkResult<GetGroupsResponse>>
}

