package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ApiRepository {
    suspend fun signup(request: SignupRequest): Flow<NetworkResult<SignupResponse>>
    suspend fun login(request: LoginRequest): Flow<NetworkResult<LoginResponse>>
    suspend fun getUserInfo(): Flow<NetworkResult<UserInfoResponse>>

    suspend fun updateProfilePhoto(
        photo: MultipartBody.Part
    ): Flow<NetworkResult<UserInfoResponse>>
    
    suspend fun createGroup(
        name: String,
        description: String,
        groupType: String,
        subject: String,
        maxMembers: Int,
        groupIcon: MultipartBody.Part? = null
    ): Flow<NetworkResult<CreateGroupResponse>>

    suspend fun getGroups(): Flow<NetworkResult<GetGroupsResponse>>

    suspend fun myGroups(): Flow<NetworkResult<MyGroupsResponse>>

    suspend fun getMaterials(
        search: String?,
        category: String?,
        sort: String?,
        page: Int?
    ): Flow<NetworkResult<MaterialsListResponse>>

    suspend fun getMaterial(id: Int): Flow<NetworkResult<MaterialDetailResponse>>

    suspend fun getCategories(): Flow<NetworkResult<CategoriesResponse>>

    suspend fun uploadMaterial(
        title: String,
        description: String,
        category: String,
        price: Double,
        file: MultipartBody.Part,
        thumbnail: MultipartBody.Part?
    ): Flow<NetworkResult<MaterialDetailResponse>>

    suspend fun deleteMaterial(id: Int): Flow<NetworkResult<MaterialDeleteResponse>>

    suspend fun rateMaterial(id: Int, rating: Int): Flow<NetworkResult<MaterialDetailResponse>>

    suspend fun generateMaterialOtp(id: Int): Flow<NetworkResult<MaterialOtpResponse>>

    suspend fun redeemMaterialOtp(id: Int, otp: String): Flow<NetworkResult<MaterialRedeemResponse>>

    suspend fun myMaterials(): Flow<NetworkResult<MyMaterialsListResponse>>

    suspend fun getMaterialAccess(id: Int): Flow<NetworkResult<MaterialAccessListResponse>>

    suspend fun joinGroupWithCode(
        invitation_code: String
    ): Flow<NetworkResult<JoinGroupWithCodeResponse>>

    suspend fun getGroupDetails(groupId: Int): Flow<NetworkResult<GetGroupDetailsResponse>>

    suspend fun updateGroupIcon(
        groupId: Int,
        groupIcon: MultipartBody.Part
    ): Flow<NetworkResult<GroupMutationResponse>>

    suspend fun uploadGroupFile(
        groupId: Int,
        title: String,
        category: String,
        file: MultipartBody.Part,
        thumbnail: MultipartBody.Part?
    ): Flow<NetworkResult<GroupUploadResponse>>

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
