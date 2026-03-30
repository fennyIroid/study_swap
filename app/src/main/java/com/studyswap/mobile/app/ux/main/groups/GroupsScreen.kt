package com.studyswap.mobile.app.ux.main.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.studyswap.mobile.app.ui.compose.common.BottomNavigationBar
import com.studyswap.mobile.app.ui.compose.common.FilterChips
import com.studyswap.mobile.app.ui.compose.common.SearchBar
import com.studyswap.mobile.app.data.source.remote.model.GroupData
import com.studyswap.mobile.app.ux.container.groupdetails.GroupDetailsRoute
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray

@Composable
fun GroupsScreen(
    navController: NavController? = null,
    viewModel: GroupsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    val sectionTitle = when (uiState.selectedFilter) {
        "My Groups" -> "Your groups"
        "Discover" -> "Discover"
        else -> "All groups"
    }
    val browseList = when (uiState.selectedFilter) {
        "My Groups" -> uiState.joinedGroups
        else -> uiState.allGroups
    }
    val showJoinOnCards = uiState.selectedFilter != "My Groups"

    // Refresh groups every time this screen resumes
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, lifecycleEvent ->
            if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
                event(GroupsUiEvent.LoadGroups)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryPeach.copy(alpha = 0.8f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp, 32.dp)
                                        .background(Color.White, RoundedCornerShape(4.dp))
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(Color.White, CircleShape)
                                    .padding(2.dp)
                                    .background(Color(0xFF8DCFA7), CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Groups",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextCharcoal
                            )
                        )
                    }

                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f))
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Search Bar
            item {
                SearchBar(modifier = Modifier.padding(horizontal = 24.dp), placeholder = "Search for groups or topics...")
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Start a Study Circle card
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(PrimaryOlive.copy(alpha = 0.8f))
                        .clickable { navController?.navigate(com.studyswap.mobile.app.ux.container.creategroup.CreateGroupRoute.routeDefinition.value) }
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Start a Study Circle",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Create a private or public group for your classmates",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            // Join a group card
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(SecondaryPeach.copy(alpha = 0.7f))
                        .clickable { navController?.navigate(com.studyswap.mobile.app.ux.container.joingroup.JoinGroupRoute.routeDefinition.value) }
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Join a group",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Find groups by invite code or browse communities",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedGray
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(PrimaryOlive.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "→",
                                style = MaterialTheme.typography.titleLarge,
                                color = PrimaryOlive
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Filter Chips
            item {
                FilterChips(
                    selectedFilter = uiState.selectedFilter,
                    onFilterSelected = { event(GroupsUiEvent.OnFilterChanged(it)) }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            uiState.infoMessage?.let { msg ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(PrimaryOlive.copy(alpha = 0.15f))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = msg,
                            modifier = Modifier.weight(1f),
                            color = TextCharcoal,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "OK",
                            color = PrimaryOlive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { event(GroupsUiEvent.OnDismissInfo) }
                        )
                    }
                }
            }

            uiState.errorMessage?.let { err ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF0F0))
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = err,
                            modifier = Modifier.weight(1f),
                            color = Color(0xFFB00020),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Dismiss",
                            color = Color(0xFFB00020),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { event(GroupsUiEvent.OnDismissError) }
                        )
                    }
                }
            }

            // Loading Indicator
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryOlive)
                    }
                }
            }

            if (uiState.selectedFilter == "My Groups" && uiState.pendingGroups.isNotEmpty()) {
                item {
                    Text(
                        text = "Awaiting approval",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        ),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(uiState.pendingGroups) { group ->
                    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        AllGroupCard(
                            group = group,
                            navController = navController,
                            pendingApproval = true
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Main list for current filter
            item {
                Text(
                    text = sectionTitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextCharcoal
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (browseList.isEmpty() && !uiState.isLoading) {
                item {
                    Text(
                        text = when (uiState.selectedFilter) {
                            "My Groups" -> "You have not joined any groups yet."
                            else -> "No groups found. Be the first to create one!"
                        },
                        color = TextMutedGray,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(browseList) { group ->
                    val gid = group.id
                    Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        AllGroupCard(
                            group = group,
                            navController = navController,
                            onRequestJoin = if (showJoinOnCards && gid != null) {
                                { event(GroupsUiEvent.OnSendJoinRequest(gid)) }
                            } else null,
                            joinRequested = gid != null && gid in uiState.requestedGroupIds
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        // Floating bottom nav bar overlay
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavigationBar(navController)
        }
    }
}

@Composable
fun AllGroupCard(
    group: GroupData,
    navController: NavController? = null,
    onRequestJoin: (() -> Unit)? = null,
    joinRequested: Boolean = false,
    pendingApproval: Boolean = false
) {
    val iconInitial = group.name?.firstOrNull()?.uppercaseChar()?.toString() ?: "G"
    val iconColor = if ((group.id ?: 0) % 2 == 0) PrimaryOlive else SecondaryPeach

    androidx.compose.material3.Card(
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        group.id?.let { id ->
                            navController?.navigate(GroupDetailsRoute.createRoute(id).value)
                        }
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(iconColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = iconInitial,
                            color = iconColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = group.name ?: "Unnamed Group",
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${group.subject ?: group.groupType ?: ""} • ${if (group.isPublic == 1) "Public" else "Private"}",
                            color = TextMutedGray,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }
            when {
                pendingApproval -> {
                    Text(
                        text = "Pending",
                        color = TextMutedGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                onRequestJoin != null -> {
                    Box(
                        modifier = Modifier
                            .clickable(enabled = !joinRequested) { onRequestJoin() }
                            .background(
                                if (joinRequested) Color.LightGray.copy(alpha = 0.35f) else PrimaryOlive.copy(alpha = 0.1f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (joinRequested) "Requested" else "Join",
                            color = if (joinRequested) TextMutedGray else PrimaryOlive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}
