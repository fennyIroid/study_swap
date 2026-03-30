package com.studyswap.mobile.app.ux.container.uploadgroupfile

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.studyswap.mobile.app.navigation.NavComposeRoute
import com.studyswap.mobile.app.navigation.NavRoute
import com.studyswap.mobile.app.navigation.NavRouteDefinition

object UploadGroupFileRoute : NavComposeRoute() {
    override val routeDefinition: NavRouteDefinition =
        NavRouteDefinition("upload_group_file/{groupId}")

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument("groupId") {
            type = NavType.StringType
            defaultValue = "0"
        }
    )

    fun createRoute(groupId: Int): NavRoute = NavRoute("upload_group_file/$groupId")
}
