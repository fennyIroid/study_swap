package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import com.studyswap.mobile.app.data.source.remote.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.RequestBody.Companion.asRequestBody
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

    override suspend fun getUserInfo(request: UserInfoRequest): Flow<NetworkResult<UserInfoResponse>> = flow {
        try {
            val response = apiServices.getUserInfo(request)
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


    private fun ResponseBody?.extractError(): String {
        return this?.string() ?: "Unknown error"
    }
    override suspend fun createGroup(
        name: String,
        description: String,
        groupType: String,
        subject: String,
        maxMembers: Int
    ): Flow<NetworkResult<CreateGroupResponse>> = flow {
        try {
            val response = apiServices.createGroup(
                name, description, groupType, subject, maxMembers
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
