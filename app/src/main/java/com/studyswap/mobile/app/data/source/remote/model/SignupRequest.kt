package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("university_id") val universityId: Int
)
