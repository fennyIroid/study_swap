package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class GroupData(
    @SerializedName("id") val id: Int?,
    @SerializedName("creator_id") val creatorId: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("group_icon") val groupIcon: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("group_type") val groupType: String?,
    @SerializedName("subject") val subject: String?,
    @SerializedName("max_members") val maxMembers: Int?,
    @SerializedName("is_public") val isPublic: Int?,
    @SerializedName("approval_required") val approvalRequired: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("last_activity_at") val lastActivityAt: String?,
    @SerializedName("invite_link") val inviteLink: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("deleted_at") val deletedAt: String?
)

data class GetGroupsResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: List<GroupData>?
)
