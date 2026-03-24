package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class JoinGroupWithCodeResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: GroupData? = null
)
