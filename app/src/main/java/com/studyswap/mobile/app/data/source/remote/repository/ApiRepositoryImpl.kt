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
        maxMembers: Int,
        isPublic: Int,
        approvalRequired: Int,
        groupIcon: java.io.File?
    ): Flow<NetworkResult<CreateGroupResponse>> = flow {
        try {
            val textMediaType = "text/plain".toMediaTypeOrNull()
            val nameBody = name.toRequestBody(textMediaType)
            val descBody = description.toRequestBody(textMediaType)
            val typeBody = groupType.toRequestBody(textMediaType)
            val subBody = subject.toRequestBody(textMediaType)
            val maxBody = maxMembers.toString().toRequestBody(textMediaType)
            val publicBody = isPublic.toString().toRequestBody(textMediaType)
            val approvalBody = approvalRequired.toString().toRequestBody(textMediaType)
            
            val iconPart = groupIcon?.let { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                okhttp3.MultipartBody.Part.createFormData("group_icon", file.name, requestFile)
            }

            val response = apiServices.createGroup(
                nameBody, descBody, typeBody, subBody, maxBody, publicBody, approvalBody, iconPart
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
}
