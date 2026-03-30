package com.studyswap.mobile.app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class MaterialUploaderDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("full_name") val fullName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("profile_image") val profileImage: String? = null
)

data class StudyMaterialDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("price") val price: String? = null,
    @SerializedName("rating") val rating: String? = null,
    @SerializedName("file_url") val fileUrl: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("user") val user: MaterialUploaderDto? = null,
    @SerializedName("has_access") val hasAccess: Boolean? = null
)

data class MaterialsPageData(
    @SerializedName("current_page") val currentPage: Int? = null,
    @SerializedName("data") val data: List<StudyMaterialDto>? = null,
    @SerializedName("first_page_url") val firstPageUrl: String? = null,
    @SerializedName("last_page") val lastPage: Int? = null,
    @SerializedName("per_page") val perPage: Int? = null,
    @SerializedName("total") val total: Int? = null
)

data class MaterialsListResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: MaterialsPageData? = null
)

data class MaterialDetailResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: StudyMaterialDto? = null
)

data class CategoriesResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: List<String>? = null
)

data class RateMaterialRequest(
    @SerializedName("rating") val rating: Int
)

data class MaterialDeleteResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null
)

data class MaterialOtpDataDto(
    @SerializedName("material_id") val materialId: Int? = null,
    @SerializedName("otp") val otp: String? = null,
    @SerializedName("expires_at") val expiresAt: String? = null,
    @SerializedName("server_time") val serverTime: String? = null
)

data class MaterialOtpResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: MaterialOtpDataDto? = null
)

data class RedeemMaterialOtpRequest(
    @SerializedName("otp") val otp: String
)

data class MaterialRedeemResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null
)

data class MaterialAccessEntryDto(
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("granted_at") val grantedAt: String? = null,
    @SerializedName("user") val user: MaterialUploaderDto? = null
)

data class MyMaterialDashboardRowDto(
    @SerializedName("material") val material: StudyMaterialDto? = null,
    @SerializedName("purchase_count") val purchaseCount: Int? = null,
    @SerializedName("access_list") val accessList: List<MaterialAccessEntryDto>? = null
)

data class MyMaterialsListResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: List<MyMaterialDashboardRowDto>? = null
)

data class MaterialAccessListResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: List<MaterialAccessEntryDto>? = null
)

fun String?.toApiDouble(): Double = this?.toDoubleOrNull() ?: 0.0
