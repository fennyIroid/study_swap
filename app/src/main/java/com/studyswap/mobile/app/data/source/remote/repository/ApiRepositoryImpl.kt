package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : ApiRepository {

    override suspend fun signup(request: SignupRequest): Flow<NetworkResult<SignupResponse>> = flow {
        try {
            val response = apiServices.signup(request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network failure"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message))
        }
    }.onStart { emit(NetworkResult.Loading()) }.flowOn(Dispatchers.IO).catch { emit(NetworkResult.Error(it.message)) }

    override suspend fun login(request: LoginRequest): Flow<NetworkResult<LoginResponse>> = flow {
        try {
            val response = apiServices.login(request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network failure"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message))
        }
    }.onStart { emit(NetworkResult.Loading()) }.flowOn(Dispatchers.IO).catch { emit(NetworkResult.Error(it.message)) }

    override suspend fun getUserInfo(): Flow<NetworkResult<UserInfoResponse>> = flow {
        try {
            val response = apiServices.getUserInfo()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Network failure"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message))
        }
    }.onStart { emit(NetworkResult.Loading()) }.flowOn(Dispatchers.IO).catch { emit(NetworkResult.Error(it.message)) }

    override suspend fun updateProfilePhoto(
        photo: MultipartBody.Part
    ): Flow<NetworkResult<UserInfoResponse>> = flow {
        try {
            val response = apiServices.updateProfilePhoto(photo)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    private fun ResponseBody?.extractError(): String {
        return this?.string() ?: "Unknown error"
    }
    override suspend fun createGroup(
        name: String,
        description: String,
        groupType: String,
        subject: String,
        maxMembers: Int,
        groupIcon: MultipartBody.Part?
    ): Flow<NetworkResult<CreateGroupResponse>> = flow {
        try {
            val textPlain = "text/plain".toMediaTypeOrNull()
            val response = apiServices.createGroup(
                name.toRequestBody(textPlain),
                description.toRequestBody(textPlain),
                groupType.toRequestBody(textPlain),
                subject.toRequestBody(textPlain),
                maxMembers.toString().toRequestBody(textPlain),
                groupIcon
            )

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                val errorMsg = response.errorBody()?.string()
                emit(NetworkResult.Error("Create group Failed: $errorMsg"))
            }
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.message() ?: "An error occurred"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getGroups(): Flow<NetworkResult<GetGroupsResponse>> = flow {
        try {
            val response = apiServices.getGroups()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error("Failed to fetch groups: ${response.errorBody()?.string()}"))
            }
        } catch (e: HttpException) {
            emit(NetworkResult.Error(e.message() ?: "An error occurred"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun myGroups(): Flow<NetworkResult<MyGroupsResponse>> = flow {
        try {
            val response = apiServices.myGroups()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMaterials(
        search: String?,
        category: String?,
        sort: String?,
        page: Int?
    ): Flow<NetworkResult<MaterialsListResponse>> = flow {
        try {
            val q = search?.trim()?.takeIf { it.isNotEmpty() }
            val c = category?.trim()?.takeIf { it.isNotEmpty() }
            val response = apiServices.getMaterials(q, c, sort, page)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMaterial(id: Int): Flow<NetworkResult<MaterialDetailResponse>> = flow {
        try {
            val response = apiServices.getMaterial(id)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getCategories(): Flow<NetworkResult<CategoriesResponse>> = flow {
        try {
            val response = apiServices.getCategories()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun uploadMaterial(
        title: String,
        description: String,
        category: String,
        price: Double,
        file: MultipartBody.Part,
        thumbnail: MultipartBody.Part?
    ): Flow<NetworkResult<MaterialDetailResponse>> = flow {
        try {
            val textPlain = "text/plain".toMediaTypeOrNull()
            val response = apiServices.uploadMaterial(
                title.toRequestBody(textPlain),
                description.toRequestBody(textPlain),
                category.toRequestBody(textPlain),
                price.toString().toRequestBody(textPlain),
                file,
                thumbnail
            )
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteMaterial(id: Int): Flow<NetworkResult<MaterialDeleteResponse>> = flow {
        try {
            val response = apiServices.deleteMaterial(id)
            if (response.isSuccessful) {
                emit(NetworkResult.Success(response.body() ?: MaterialDeleteResponse()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun rateMaterial(id: Int, rating: Int): Flow<NetworkResult<MaterialDetailResponse>> = flow {
        try {
            val response = apiServices.rateMaterial(id, RateMaterialRequest(rating))
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun generateMaterialOtp(id: Int): Flow<NetworkResult<MaterialOtpResponse>> = flow {
        try {
            val response = apiServices.generateMaterialOtp(id)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun redeemMaterialOtp(id: Int, otp: String): Flow<NetworkResult<MaterialRedeemResponse>> = flow {
        try {
            val response = apiServices.redeemMaterialOtp(id, RedeemMaterialOtpRequest(otp))
            if (response.isSuccessful) {
                emit(NetworkResult.Success(response.body() ?: MaterialRedeemResponse()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun myMaterials(): Flow<NetworkResult<MyMaterialsListResponse>> = flow {
        try {
            val response = apiServices.myMaterials()
            if (response.isSuccessful) {
                emit(NetworkResult.Success(response.body() ?: MyMaterialsListResponse(data = emptyList())))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun getMaterialAccess(id: Int): Flow<NetworkResult<MaterialAccessListResponse>> = flow {
        try {
            val response = apiServices.getMaterialAccess(id)
            if (response.isSuccessful) {
                emit(NetworkResult.Success(response.body() ?: MaterialAccessListResponse(data = emptyList())))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
    }.flowOn(Dispatchers.IO)

    override suspend fun joinGroupWithCode(
        invitationCode: String
    ): Flow<NetworkResult<JoinGroupWithCodeResponse>> = flow {
        try {
            val textMediaType = "text/plain".toMediaTypeOrNull()
            val invitationCodeBody = invitationCode.toRequestBody(textMediaType)
            val response = apiServices.joinGroupWithCode(invitationCodeBody)

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }
        .catch { e ->
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
        .flowOn(Dispatchers.IO)

    override suspend fun getGroupDetails(groupId: Int): Flow<NetworkResult<GetGroupDetailsResponse>> =
        flow {
            try {
                val response = apiServices.getGroupDetails(groupId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(
                        NetworkResult.Error(
                            "Failed to fetch group details: ${response.errorBody()?.string()}"
                        )
                    )
                }
            } catch (e: HttpException) {
                emit(NetworkResult.Error(e.message() ?: "An error occurred"))
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun updateGroupIcon(
        groupId: Int,
        groupIcon: MultipartBody.Part
    ): Flow<NetworkResult<GroupMutationResponse>> = flow {
        try {
            val response = apiServices.updateGroupIcon(groupId, groupIcon)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }
        .catch { e ->
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
        .flowOn(Dispatchers.IO)

    override suspend fun uploadGroupFile(
        groupId: Int,
        title: String,
        category: String,
        file: MultipartBody.Part,
        thumbnail: MultipartBody.Part?
    ): Flow<NetworkResult<GroupUploadResponse>> = flow {
        try {
            val textPlain = "text/plain".toMediaTypeOrNull()
            val response = apiServices.uploadGroupFile(
                groupId = groupId,
                title = title.toRequestBody(textPlain),
                category = category.toRequestBody(textPlain),
                file = file,
                thumbnail = thumbnail
            )
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(NetworkResult.Error(response.errorBody().extractError()))
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }
        .catch { e ->
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
        .flowOn(Dispatchers.IO)

    override suspend fun setGroupSettings(
        groupId: Int,
        allowMemberInvite: Boolean,
        allowFileShare: Boolean,
        allowChat: Boolean
    ): Flow<NetworkResult<SetGroupSettingsResponse>> = flow {
        try {
            val request = SetGroupSettingsRequest(
                allowMemberInvite = if (allowMemberInvite) 1 else 0,
                allowFileShare = if (allowFileShare) 1 else 0,
                allowChat = if (allowChat) 1 else 0
            )
            val response = apiServices.setGroupSettings(groupId, request)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()))
            } else {
                emit(
                    NetworkResult.Error(
                        response.errorBody().extractError()
                    )
                )
            }
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: HttpException) {
            if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
            else emit(NetworkResult.Error(e.message()))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
    }.onStart { emit(NetworkResult.Loading()) }
        .catch { e ->
            emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
        }
        .flowOn(Dispatchers.IO)

    override suspend fun sendGroupRequest(groupId: Int): Flow<NetworkResult<SendGroupRequestResponse>> =
        flow {
            try {
                val response = apiServices.sendGroupRequest(groupId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun acceptGroupRequest(requestId: Int): Flow<NetworkResult<AcceptGroupRequestResponse>> =
        flow {
            try {
                val response = apiServices.acceptGroupRequest(requestId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun rejectGroupRequest(requestId: Int): Flow<NetworkResult<RejectGroupRequestResponse>> =
        flow {
            try {
                val response = apiServices.rejectGroupRequest(requestId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun setGroupMemberRole(
        groupId: Int,
        userId: Int,
        role: String
    ): Flow<NetworkResult<SetGroupMemberRoleResponse>> =
        flow {
            try {
                val request = SetGroupMemberRoleRequest(role = role)
                val response = apiServices.setGroupMemberRole(groupId, userId, request)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun removeGroupMember(
        groupId: Int,
        userId: Int
    ): Flow<NetworkResult<RemoveGroupMemberResponse>> =
        flow {
            try {
                val response = apiServices.removeGroupMember(groupId, userId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun leaveGroup(
        groupId: Int
    ): Flow<NetworkResult<LeaveGroupResponse>> =
        flow {
            try {
                val response = apiServices.leaveGroup(groupId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)

    override suspend fun generateInviteLink(
        groupId: Int
    ): Flow<NetworkResult<GenerateInviteLinkResponse>> =
        flow {
            try {
                val response = apiServices.generateInviteLink(groupId)
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()))
                } else {
                    emit(NetworkResult.Error(response.errorBody().extractError()))
                }
            } catch (e: IOException) {
                emit(NetworkResult.Error("Please check your network connection"))
            } catch (e: HttpException) {
                if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
                else emit(NetworkResult.Error(e.message()))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
        }.onStart { emit(NetworkResult.Loading()) }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "An unknown error occurred"))
            }
            .flowOn(Dispatchers.IO)
}
