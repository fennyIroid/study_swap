package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class UserInfoRequest(
    @SerializedName("user_id") val userId: Int
)
