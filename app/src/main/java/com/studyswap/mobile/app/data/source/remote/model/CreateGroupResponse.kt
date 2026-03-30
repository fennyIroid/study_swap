package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CreateGroupResponse(
    @SerializedName("error") val error: Boolean? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: CreateGroupDataPayload? = null
)

data class CreateGroupDataPayload(
    @SerializedName("group") val group: GroupData? = null,
    @SerializedName("invitation_code") val invitationCode: String? = null
)
