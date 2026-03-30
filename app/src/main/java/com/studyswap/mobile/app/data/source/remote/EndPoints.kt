package com.studyswap.mobile.app.data.source.remote

import com.studyswap.mobile.app.BuildConfig

object EndPoints {
    object URLs {
        const val BASE_URL: String = BuildConfig.BASE_URL
    }
    object Auth {
        const val SIGNUP = "api/signup"
        const val LOGIN = "api/login"
        const val GET_USER_INFO = "api/get_user_info"
        const val UPDATE_PROFILE_PHOTO = "api/update_profile_photo"
    }
    object Material {
        const val MATERIALS = "api/materials"
        const val MATERIAL_DETAIL = "api/materials/{id}"
        const val CATEGORIES = "api/categories"
        const val MATERIAL_RATE = "api/materials/{id}/rate"
        const val MATERIAL_OTP = "api/materials/{id}/otp"
        const val MATERIAL_REDEEM = "api/materials/{id}/redeem"
        const val MY_MATERIALS = "api/my_materials"
        const val MATERIAL_ACCESS = "api/materials/{id}/access"
    }
    object Group {
        const val CREATE_GROUP = "api/create_group"
        const val GET_GROUPS = "api/get_groups"
        const val MY_GROUPS = "api/my_groups"
        const val JOIN_GROUP_WITH_CODE = "api/join_group_with_code"
        const val GET_GROUP_DETAILS = "api/get_group_details/{group_id}"
        const val SET_GROUP_SETTING = "api/set_group_setting/{group_id}"
        const val SEND_GROUP_REQUEST = "api/send_group_request/{group_id}"
        const val ACCEPT_GROUP_REQUEST = "api/accept_group_request/{request_id}"
        const val REJECT_GROUP_REQUEST = "api/reject_group_request/{request_id}"
        const val SET_GROUP_MEMBER_ROLE = "api/set_group_member_role/{group_id}/{user_id}"
        const val REMOVE_GROUP_MEMBER = "api/remove_group_member/{group_id}/{user_id}"
        const val LEAVE_GROUP = "api/leave_group/{group_id}"
        const val GENERATE_INVITE_LINK = "api/generate_invite_link/{group_id}"
        const val GROUP_ICON = "api/groups/{group_id}/icon"
        const val GROUP_UPLOADS = "api/groups/{group_id}/uploads"
    }
}
