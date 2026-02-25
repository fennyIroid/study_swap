package com.studyswap.mobile.app.data.source.remote.repository

import com.studyswap.mobile.app.data.source.remote.helper.NetworkResult
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : ApiRepository {

    // Template for a single API call - copy and adapt for each endpoint:
    // override suspend fun someApi(...): Flow<NetworkResult<SomeResponse>> = flow {
    //     try {
    //         val response = apiServices.someApi(...)
    //         if (response.isSuccessful && response.body() != null) {
    //             emit(NetworkResult.Success(response.body()))
    //         } else {
    //             emit(NetworkResult.Error(response.errorBody().extractError()))
    //         }
    //     } catch (e: IOException) {
    //         emit(NetworkResult.Error(e.message))
    //     } catch (e: HttpException) {
    //         if (e.code() == 401) emit(NetworkResult.UnAuthenticated(e.message))
    //         else emit(NetworkResult.Error(e.message))
    //     }
    // }.onStart { emit(NetworkResult.Loading()) }.flowOn(Dispatchers.IO).catch { emit(NetworkResult.Error(it.message)) }
}
