package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.EndPoints
import com.studyswap.mobile.app.data.source.remote.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiServices {
    @POST(EndPoints.Auth.SIGNUP)
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST(EndPoints.Auth.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @HTTP(method = "POST", path = EndPoints.Auth.GET_USER_INFO, hasBody = false)
    suspend fun getUserInfo(): Response<UserInfoResponse>

    @Multipart
    @POST(EndPoints.Auth.UPDATE_PROFILE_PHOTO)
    suspend fun updateProfilePhoto(
        @Part("photo") photo: MultipartBody.Part
    ): Response<UserInfoResponse>

    @Multipart
    @POST(EndPoints.Group.CREATE_GROUP)
    suspend fun createGroup(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("group_type") groupType: RequestBody,
        @Part("subject") subject: RequestBody,
        @Part("max_members") maxMembers: RequestBody,
        @Part("group_icon") groupIcon: MultipartBody.Part?
    ): Response<CreateGroupResponse>

    @retrofit2.http.GET(EndPoints.Group.GET_GROUPS)
    suspend fun getGroups(): Response<GetGroupsResponse>

    @retrofit2.http.GET(EndPoints.Group.MY_GROUPS)
    suspend fun myGroups(): Response<MyGroupsResponse>

    @retrofit2.http.GET(EndPoints.Material.MATERIALS)
    suspend fun getMaterials(
        @retrofit2.http.Query("search") search: String?,
        @retrofit2.http.Query("category") category: String?,
        @retrofit2.http.Query("sort") sort: String?,
        @retrofit2.http.Query("page") page: Int?
    ): Response<MaterialsListResponse>

    @retrofit2.http.GET(EndPoints.Material.MATERIAL_DETAIL)
    suspend fun getMaterial(
        @retrofit2.http.Path("id") id: Int
    ): Response<MaterialDetailResponse>

    @retrofit2.http.GET(EndPoints.Material.CATEGORIES)
    suspend fun getCategories(): Response<CategoriesResponse>

    @Multipart
    @POST(EndPoints.Material.MATERIALS)
    suspend fun uploadMaterial(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category") category: RequestBody,
        @Part("price") price: RequestBody,
        @Part("file") file: MultipartBody.Part,
        @Part("thumbnail") thumbnail: MultipartBody.Part?
    ): Response<MaterialDetailResponse>

    @Multipart
    @POST(EndPoints.Group.GROUP_ICON)
    suspend fun updateGroupIcon(
        @Path("group_id") groupId: Int,
        @Part("group_icon") groupIcon: MultipartBody.Part
    ): Response<GroupMutationResponse>

    @Multipart
    @POST(EndPoints.Group.GROUP_UPLOADS)
    suspend fun uploadGroupFile(
        @Path("group_id") groupId: Int,
        @Part("title") title: RequestBody,
        @Part("category") category: RequestBody,
        @Part("file") file: MultipartBody.Part,
        @Part("thumbnail") thumbnail: MultipartBody.Part?
    ): Response<GroupUploadResponse>

    @retrofit2.http.DELETE(EndPoints.Material.MATERIAL_DETAIL)
    suspend fun deleteMaterial(
        @retrofit2.http.Path("id") id: Int
    ): Response<MaterialDeleteResponse>

    @POST(EndPoints.Material.MATERIAL_RATE)
    suspend fun rateMaterial(
        @retrofit2.http.Path("id") id: Int,
        @Body body: RateMaterialRequest
    ): Response<MaterialDetailResponse>

    @POST(EndPoints.Material.MATERIAL_OTP)
    suspend fun generateMaterialOtp(
        @retrofit2.http.Path("id") id: Int
    ): Response<MaterialOtpResponse>

    @POST(EndPoints.Material.MATERIAL_REDEEM)
    suspend fun redeemMaterialOtp(
        @retrofit2.http.Path("id") id: Int,
        @Body body: RedeemMaterialOtpRequest
    ): Response<MaterialRedeemResponse>

    @retrofit2.http.GET(EndPoints.Material.MY_MATERIALS)
    suspend fun myMaterials(): Response<MyMaterialsListResponse>

    @retrofit2.http.GET(EndPoints.Material.MATERIAL_ACCESS)
    suspend fun getMaterialAccess(
        @retrofit2.http.Path("id") id: Int
    ): Response<MaterialAccessListResponse>

    @Multipart
    @POST(EndPoints.Group.JOIN_GROUP_WITH_CODE)
    suspend fun joinGroupWithCode(
        @Part("invitation_code") invitationCode: okhttp3.RequestBody
    ): Response<JoinGroupWithCodeResponse>

    @retrofit2.http.GET(EndPoints.Group.GET_GROUP_DETAILS)
    suspend fun getGroupDetails(
        @retrofit2.http.Path("group_id") groupId: Int
    ): Response<GetGroupDetailsResponse>

    @POST(EndPoints.Group.SET_GROUP_SETTING)
    suspend fun setGroupSettings(
        @retrofit2.http.Path("group_id") groupId: Int,
        @Body request: SetGroupSettingsRequest
    ): Response<SetGroupSettingsResponse>

    @POST(EndPoints.Group.SEND_GROUP_REQUEST)
    suspend fun sendGroupRequest(
        @retrofit2.http.Path("group_id") groupId: Int
    ): Response<SendGroupRequestResponse>

    @POST(EndPoints.Group.ACCEPT_GROUP_REQUEST)
    suspend fun acceptGroupRequest(
        @retrofit2.http.Path("request_id") requestId: Int
    ): Response<AcceptGroupRequestResponse>

    @POST(EndPoints.Group.REJECT_GROUP_REQUEST)
    suspend fun rejectGroupRequest(
        @retrofit2.http.Path("request_id") requestId: Int
    ): Response<RejectGroupRequestResponse>

    @POST(EndPoints.Group.SET_GROUP_MEMBER_ROLE)
    suspend fun setGroupMemberRole(
        @retrofit2.http.Path("group_id") groupId: Int,
        @retrofit2.http.Path("user_id") userId: Int,
        @Body request: SetGroupMemberRoleRequest
    ): Response<SetGroupMemberRoleResponse>

    @POST(EndPoints.Group.REMOVE_GROUP_MEMBER)
    suspend fun removeGroupMember(
        @retrofit2.http.Path("group_id") groupId: Int,
        @retrofit2.http.Path("user_id") userId: Int
    ): Response<RemoveGroupMemberResponse>

    @POST(EndPoints.Group.LEAVE_GROUP)
    suspend fun leaveGroup(
        @retrofit2.http.Path("group_id") groupId: Int
    ): Response<LeaveGroupResponse>

    @POST(EndPoints.Group.GENERATE_INVITE_LINK)
    suspend fun generateInviteLink(
        @retrofit2.http.Path("group_id") groupId: Int
    ): Response<GenerateInviteLinkResponse>
}
