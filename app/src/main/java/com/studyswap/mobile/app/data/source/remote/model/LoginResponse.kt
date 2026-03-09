package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("api_key") val token: String? = null,
    @SerializedName("user_id") val userId: Int? = null
)
