package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class SetGroupMemberRoleRequest(
    @SerializedName("role") val role: String
)

data class SetGroupMemberRoleResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?
)

