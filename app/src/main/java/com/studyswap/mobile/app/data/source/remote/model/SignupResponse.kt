package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null
)
