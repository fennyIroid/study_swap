package com.studyswap.mobile.app.model.domain.response

/**
 * Generic API response wrapper. Use for endpoints that return { data, message, status, apiErrors }.
 */
data class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Boolean = false,
    val apiErrors: List<ApiError>? = null
)

data class ApiError(
    val key: String? = null,
    val value: List<String>? = null
)
