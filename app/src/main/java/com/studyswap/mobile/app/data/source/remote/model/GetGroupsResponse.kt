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

// Detailed group response ----------------------------------------------------

data class GroupSettingsData(
    @SerializedName("group_id") val groupId: Int?,
    @SerializedName("allow_member_invite") val allowMemberInvite: Int?,
    @SerializedName("allow_file_share") val allowFileShare: Int?,
    @SerializedName("allow_chat") val allowChat: Int?
)

data class GroupMemberData(
    @SerializedName("id") val id: Int?,
    @SerializedName("group_id") val groupId: Int?,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("role") val role: String?,
    @SerializedName("joined_at") val joinedAt: String?
)

data class GroupDetailsPayload(
    @SerializedName("group") val group: GroupData?,
    @SerializedName("settings") val settings: GroupSettingsData?,
    @SerializedName("members") val members: List<GroupMemberData>?
)

data class GetGroupDetailsResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: GroupDetailsPayload?
)

data class GenerateInviteLinkResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: GroupData?
)
