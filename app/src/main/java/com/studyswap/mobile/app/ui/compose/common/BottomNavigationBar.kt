package com.studyswap.mobile.app.ui.compose.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.TextMutedGray
import com.studyswap.mobile.app.ux.container.profile.ProfileRoute
import com.studyswap.mobile.app.ux.main.groups.GroupsRoute
import com.studyswap.mobile.app.ux.main.home.HomeRoute
import com.studyswap.mobile.app.ux.main.marketplace.MarketplaceRoute

private data class NavItem(
    val label: String,
    val route: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector
)

@Composable
fun BottomNavigationBar(navController: NavController? = null) {
    val navBackStackEntry by navController?.currentBackStackEntryAsState() ?: remember { mutableStateOf(null) }
    val currentDestination = navBackStackEntry?.destination

    val items = remember {
        listOf(
            NavItem("Home", HomeRoute.routeDefinition.value, Icons.Outlined.Home, Icons.Rounded.Home),
            NavItem("Groups", GroupsRoute.routeDefinition.value, Icons.Outlined.Groups, Icons.Rounded.Groups),
            NavItem("Market", MarketplaceRoute.routeDefinition.value, Icons.Outlined.Storefront, Icons.Rounded.Storefront),
            NavItem("Profile", ProfileRoute.routeDefinition.value, Icons.Outlined.Person, Icons.Rounded.Person)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .navigationBarsPadding()
    ) {
        // Glassmorphic container with border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.08f)
                )
                .clip(RoundedCornerShape(26.dp))
                .background(Color.White.copy(alpha = 0.95f))
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE8E8E8),
                            Color(0xFFD5D5D5)
                        )
                    ),
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentDestination
                        ?.hierarchy
                        ?.any { destination -> destination.route == item.route } == true
                    NavBarItem(
                        item = item,
                        isSelected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController?.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavBarItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryOlive else TextMutedGray,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "iconColor"
    )

    val labelColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryOlive else TextMutedGray,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "labelColor"
    )

    val bgAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "bgAlpha"
    )

    val iconOffset by animateDpAsState(
        targetValue = if (isSelected) (-2).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offset"
    )

    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 20.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "indicator"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .scale(animatedScale)
    ) {
        // Pill background behind icon
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(PrimaryOlive.copy(alpha = bgAlpha * 0.12f))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier
                    .size(24.dp)
                    .offset(y = iconOffset)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Label
        Text(
            text = item.label,
            color = labelColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            letterSpacing = if (isSelected) 0.3.sp else 0.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Active indicator line
        Box(
            modifier = Modifier
                .width(indicatorWidth)
                .height(3.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected)
                        Brush.horizontalGradient(listOf(PrimaryOlive, SecondaryPeach))
                    else
                        Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
                )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBF9F6)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
