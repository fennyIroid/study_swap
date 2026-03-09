package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("login_type") val loginType: String = "email"
)
