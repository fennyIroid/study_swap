package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("error") val error: Boolean? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val payload: UserInfoDataPayload? = null
) {
    val userData: UserData? get() = payload?.user?.copy(
        university = payload.university?.name ?: payload.user.university
    )
}

data class UserInfoDataPayload(
    @SerializedName("user") val user: UserData? = null,
    @SerializedName("university") val university: UniversityData? = null
)

data class UniversityData(
    @SerializedName("name") val name: String? = null
)

data class UserData(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("handle") val handle: String? = null,
    @SerializedName("university") val university: String? = null,
    @SerializedName("major") val major: String? = null,
    @SerializedName("uploads") val uploads: Int? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("reputation") val reputation: Int? = null,
    @SerializedName("profile_image") val profileImage: String? = null
)
