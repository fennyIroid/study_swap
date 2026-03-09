package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.EndPoints
import com.studyswap.mobile.app.data.source.remote.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST(EndPoints.Auth.SIGNUP)
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST(EndPoints.Auth.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST(EndPoints.Auth.GET_USER_INFO)
    suspend fun getUserInfo(@Body request: UserInfoRequest): Response<UserInfoResponse>

    @retrofit2.http.Multipart
    @POST(EndPoints.Group.CREATE_GROUP)
    suspend fun createGroup(
        @retrofit2.http.Part("name") name: okhttp3.RequestBody,
        @retrofit2.http.Part("description") description: okhttp3.RequestBody,
        @retrofit2.http.Part("group_type") groupType: okhttp3.RequestBody,
        @retrofit2.http.Part("subject") subject: okhttp3.RequestBody,
        @retrofit2.http.Part("max_members") maxMembers: okhttp3.RequestBody,
        @retrofit2.http.Part("is_public") isPublic: okhttp3.RequestBody,
        @retrofit2.http.Part("approval_required") approvalRequired: okhttp3.RequestBody,
        @retrofit2.http.Part groupIcon: okhttp3.MultipartBody.Part?
    ): Response<CreateGroupResponse>

    @retrofit2.http.GET(EndPoints.Group.GET_GROUPS)
    suspend fun getGroups(): Response<GetGroupsResponse>
}

