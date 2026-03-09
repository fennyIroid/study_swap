package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.studyswap.mobile.app.navigation.NavComposeRoute
import com.studyswap.mobile.app.navigation.NavRoute
import com.studyswap.mobile.app.navigation.NavRouteDefinition

object MarketplaceItemDetailRoute : NavComposeRoute() {
    override val routeDefinition: NavRouteDefinition = NavRouteDefinition("marketplace_item/{itemId}")

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument("itemId") {
            type = NavType.StringType
            defaultValue = ""
        }
    )

    fun createRoute(itemId: String): NavRoute = NavRoute("marketplace_item/$itemId")
}
