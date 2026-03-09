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
    }
    object Group {
        const val CREATE_GROUP = "api/create_group"
        const val GET_GROUPS = "api/get_groups"
    }
}
