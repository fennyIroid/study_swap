package com.example.studyswap.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studyswap.mobile.app.R
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SurfaceWhite
import com.studyswap.mobile.app.ui.theme.TextMutedGray


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.studyswap.mobile.app.ux.main.home.HomeRoute
import com.studyswap.mobile.app.ux.container.profile.ProfileRoute
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.TextCharcoal

@Composable
fun BottomNavigationBar(navController: NavController? = null) {
    val currentRoute = navController?.currentBackStackEntry?.destination?.route
    
    val items = listOf(
        Triple("Home", Icons.Outlined.Home, HomeRoute.routeDefinition.value),
        Triple("Groups", Icons.Outlined.Groups, "groups"), // Placeholder
        Triple("Center", Icons.Outlined.GridView, "center"),
        Triple("Chat", Icons.Outlined.ChatBubbleOutline, "chat"),
        Triple("Profile", Icons.Outlined.Person, ProfileRoute.routeDefinition.value)
    )

    Surface(
        color = SurfaceWhite,
        shadowElevation = 10.dp,
        modifier = Modifier,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            items.forEachIndexed { index, (label, icon, route) ->
                val isSelected = currentRoute == route
                
                if (index == 2) {
                    // Special center button
                    NavigationBarItem(
                        selected = false,
                        onClick = { /* TODO */ },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(BackgroundOffWhite)
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.GridView,
                                    contentDescription = "Menu",
                                    tint = TextCharcoal,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                } else {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = icon as ImageVector, 
                                contentDescription = label,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        label = { 
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        selected = isSelected,
                        onClick = { 
                            if (currentRoute != route && route is String) {
                                navController?.navigate(route) {
                                    popUpTo(HomeRoute.routeDefinition.value) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryOlive,
                            selectedTextColor = PrimaryOlive,
                            unselectedIconColor = TextMutedGray,
                            unselectedTextColor = TextMutedGray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
