package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("error") val error: Boolean? = null,
    @SerializedName("message") val message: String? = null
)
