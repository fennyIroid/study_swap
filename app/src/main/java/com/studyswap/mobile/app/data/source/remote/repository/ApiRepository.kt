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
        maxMembers: Int
    ): Flow<NetworkResult<CreateGroupResponse>>

    suspend fun getGroups(): Flow<NetworkResult<GetGroupsResponse>>

    suspend fun joinGroupWithCode(
        invitation_code: String
    ): Flow<NetworkResult<JoinGroupWithCodeResponse>>

    suspend fun getGroupDetails(groupId: Int): Flow<NetworkResult<GetGroupDetailsResponse>>

    suspend fun setGroupSettings(
        groupId: Int,
        allowMemberInvite: Boolean,
        allowFileShare: Boolean,
        allowChat: Boolean
    ): Flow<NetworkResult<SetGroupSettingsResponse>>

    suspend fun sendGroupRequest(groupId: Int): Flow<NetworkResult<SendGroupRequestResponse>>

    suspend fun acceptGroupRequest(requestId: Int): Flow<NetworkResult<AcceptGroupRequestResponse>>

    suspend fun rejectGroupRequest(requestId: Int): Flow<NetworkResult<RejectGroupRequestResponse>>

    suspend fun setGroupMemberRole(
        groupId: Int,
        userId: Int,
        role: String
    ): Flow<NetworkResult<SetGroupMemberRoleResponse>>

    suspend fun removeGroupMember(
        groupId: Int,
        userId: Int
    ): Flow<NetworkResult<RemoveGroupMemberResponse>>

    suspend fun leaveGroup(
        groupId: Int
    ): Flow<NetworkResult<LeaveGroupResponse>>

    suspend fun generateInviteLink(
        groupId: Int
    ): Flow<NetworkResult<GenerateInviteLinkResponse>>
}
