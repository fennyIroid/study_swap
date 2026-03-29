package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.EndPoints
import com.studyswap.mobile.app.data.source.remote.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {
    @POST(EndPoints.Auth.SIGNUP)
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST(EndPoints.Auth.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST(EndPoints.Auth.GET_USER_INFO)
    suspend fun getUserInfo(@Body request: UserInfoRequest): Response<UserInfoResponse>

    @retrofit2.http.FormUrlEncoded
    @POST(EndPoints.Group.CREATE_GROUP)
    suspend fun createGroup(
        @retrofit2.http.Field("name") name: String,
        @retrofit2.http.Field("description") description: String,
        @retrofit2.http.Field("group_type") groupType: String,
        @retrofit2.http.Field("subject") subject: String,
        @retrofit2.http.Field("max_members") maxMembers: Int
    ): Response<CreateGroupResponse>

    @retrofit2.http.GET(EndPoints.Group.GET_GROUPS)
    suspend fun getGroups(): Response<GetGroupsResponse>

    @retrofit2.http.Multipart
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
