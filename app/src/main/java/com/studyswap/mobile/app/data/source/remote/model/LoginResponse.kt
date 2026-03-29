package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName(value = "token", alternate = ["api_key", "api_token"]) val token: String? = null,
    @SerializedName(value = "user_id", alternate = ["id"]) val userId: Int? = null
)
