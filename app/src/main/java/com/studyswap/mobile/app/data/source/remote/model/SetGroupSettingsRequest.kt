package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class SetGroupSettingsRequest(
    @SerializedName("allow_member_invite") val allowMemberInvite: Int,
    @SerializedName("allow_file_share") val allowFileShare: Int,
    @SerializedName("allow_chat") val allowChat: Int
)

data class SetGroupSettingsResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?
)

