package com.studyswap.mobile.app.ux.container.groupdetails

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.studyswap.mobile.app.data.source.remote.model.GroupData
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray

@Composable
fun GroupDetailsScreen(
    navController: NavController,
    viewModel: GroupDetailsViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)

    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        event(GroupDetailsUiEvent.LoadGroupDetails)
    }

    val groupIconPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { event(GroupDetailsUiEvent.OnGroupIconPicked(it)) }
    }

    GroupDetailsContent(
        uiState = uiState,
        event = event,
        onPickGroupIcon = { groupIconPicker.launch("image/*") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupDetailsContent(
    uiState: GroupDetailsUiDataState,
    event: (GroupDetailsUiEvent) -> Unit,
    onPickGroupIcon: () -> Unit
) {
    val scrollState = rememberScrollState()
    val filteredMaterials = remember(
        uiState.materials,
        uiState.materialsTab,
        uiState.materialsSearchQuery
    ) {
        var list = uiState.materials.filter { it.type == uiState.materialsTab }
        val q = uiState.materialsSearchQuery.trim().lowercase()
        if (q.isNotEmpty()) {
            list = list.filter { it.title.lowercase().contains(q) }
        }
        if (!uiState.sortByLatest) list = list.reversed()
        list
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Group Details",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { event(GroupDetailsUiEvent.OnBackClick) },
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White, CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextCharcoal)
                    }
                },
                actions = {
                    IconButton(onClick = { event(GroupDetailsUiEvent.OnShareClick) }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = TextCharcoal)
                    }
                    IconButton(onClick = { event(GroupDetailsUiEvent.OnMenuClick) }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = TextCharcoal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundOffWhite)
            )
        },
        containerColor = BackgroundOffWhite,
        floatingActionButton = {
            val canUpload =
                uiState.group != null && (uiState.settings?.allowFileShare != false)
            if (canUpload) {
                FloatingActionButton(
                    onClick = { event(GroupDetailsUiEvent.OnAddMaterialClick) },
                    containerColor = PrimaryOlive,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add group file")
                }
            }
        }
    ) { innerPadding ->
        if (uiState.isLoading && uiState.group == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryOlive)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                uiState.group?.let { group ->
                    GroupHeaderSection(
                        group = group,
                        memberCount = uiState.memberCount,
                        isAdmin = uiState.isAdmin,
                        badgeCount = 9,
                        onChangeGroupIcon = if (uiState.isAdmin) onPickGroupIcon else null
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    GroupMetaSection(
                        group = group,
                        settings = uiState.settings
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    if (uiState.isAdmin) {
                        AdminToolsSection(
                            pendingRequests = uiState.pendingRequests,
                            unreadPendingCount = uiState.unreadPendingCount,
                            joinCode = uiState.joinCode,
                            settings = uiState.settings,
                            onSettingsChanged = { updated ->
                                event(
                                    GroupDetailsUiEvent.OnSettingsChanged(updated)
                                )
                            },
                            members = uiState.members,
                            onMemberRoleChange = { member, role ->
                                event(GroupDetailsUiEvent.OnMemberRoleChange(member, role))
                            },
                            event = event
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    MaterialsSection(
                        materials = filteredMaterials,
                        searchQuery = uiState.materialsSearchQuery,
                        selectedTab = uiState.materialsTab,
                        sortByLatest = uiState.sortByLatest,
                        onSearchChange = { event(GroupDetailsUiEvent.OnSearchMaterials(it)) },
                        onTabChange = { event(GroupDetailsUiEvent.OnMaterialsTabChange(it)) },
                        onSortClick = { event(GroupDetailsUiEvent.OnSortChange) },
                        onDownload = { event(GroupDetailsUiEvent.OnDownloadMaterial(it)) }
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    if (!uiState.isAdmin) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .clickable { event(GroupDetailsUiEvent.OnLeaveGroupClick) }
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Leave Group",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } ?: run {
                    if (!uiState.isLoading) {
                        Text(
                            text = uiState.errorMessage ?: "Group not found",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun GroupHeaderSection(
    group: GroupData,
    memberCount: Int,
    isAdmin: Boolean,
    badgeCount: Int,
    onChangeGroupIcon: (() -> Unit)? = null
) {
    val iconColor = when ((group.id ?: 0) % 3) {
        0 -> PrimaryOlive
        1 -> SecondaryPeach
        else -> Color(0xFFE07A5F)
    }
    val iconInitial = group.name?.firstOrNull()?.uppercaseChar()?.toString() ?: "G"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(RoundedCornerShape(24.dp))
                .then(
                    if (onChangeGroupIcon != null) {
                        Modifier.clickable(onClick = onChangeGroupIcon)
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            val iconUrl = group.groupIcon
            if (!iconUrl.isNullOrBlank()) {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    SecondaryPeach,
                                    iconColor,
                                    Color(0xFF5C6BC0)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = iconInitial,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp)
                    .background(PrimaryOlive, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (onChangeGroupIcon != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Tap icon to change",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedGray
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = group.name ?: "Unnamed Group",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextCharcoal
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(TextMutedGray.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = TextMutedGray
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$memberCount Members",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextCharcoal,
                    fontWeight = FontWeight.Medium
                )
            }
            if (isAdmin) {
                Box(
                    modifier = Modifier
                        .background(PrimaryOlive.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Admin",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryOlive,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun GroupMetaSection(
    group: GroupData,
    settings: GroupSettingsUi?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        group.description?.takeIf { it.isNotBlank() }?.let { desc ->
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            group.subject?.takeIf { it.isNotBlank() }?.let { subject ->
                MetaChip(label = subject)
            }
            group.groupType?.takeIf { it.isNotBlank() }?.let { type ->
                MetaChip(label = type.replaceFirstChar { it.uppercaseChar() })
            }
            group.maxMembers?.let { max ->
                MetaChip(label = "Max $max")
            }
            group.isPublic?.let { isPublic ->
                MetaChip(label = if (isPublic == 1) "Public" else "Private")
            }
        }

        settings?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MetaChip(
                    label = if (it.allowMemberInvite) "Member invites on" else "Member invites off"
                )
                MetaChip(
                    label = if (it.allowFileShare) "File sharing on" else "File sharing off"
                )
                MetaChip(
                    label = if (it.allowChat) "Chat on" else "Chat off"
                )
            }
        }
    }
}

@Composable
private fun MetaChip(label: String) {
    Box(
        modifier = Modifier
            .background(TextMutedGray.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextCharcoal
        )
    }
}

@Composable
private fun AdminToolsSection(
    pendingRequests: List<PendingRequestItem>,
    unreadPendingCount: Int,
    joinCode: String,
    settings: GroupSettingsUi?,
    onSettingsChanged: (GroupSettingsUi) -> Unit,
    members: List<GroupMemberUi>,
    onMemberRoleChange: (GroupMemberUi, String) -> Unit,
    event: (GroupDetailsUiEvent) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Admin Tools",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
            Text(
                text = "Manage Group",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryOlive,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(onClick = { event(GroupDetailsUiEvent.OnManageGroupClick) })
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "PENDING REQUESTS (${pendingRequests.size})",
                style = MaterialTheme.typography.labelMedium,
                color = TextMutedGray
            )
            if (unreadPendingCount > 0) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        pendingRequests.forEach { request ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(TextMutedGray.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = request.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                            color = TextMutedGray,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = request.name,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                        Text(
                            text = request.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGray
                        )
                    }
                    IconButton(
                        onClick = { event(GroupDetailsUiEvent.OnDeclineRequest(request)) },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Red.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Decline", tint = Color.Red)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { event(GroupDetailsUiEvent.OnAcceptRequest(request)) },
                        modifier = Modifier
                            .size(40.dp)
                            .background(PrimaryOlive.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Accept", tint = PrimaryOlive)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "JOIN CODE",
            style = MaterialTheme.typography.labelMedium,
            color = TextMutedGray
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = joinCode.ifEmpty { "—" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { event(GroupDetailsUiEvent.OnCopyJoinCode) },
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
            }
            IconButton(
                onClick = { event(GroupDetailsUiEvent.OnRefreshJoinCode) },
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }

        settings?.let { current ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Group Settings",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))

            fun toggleSettings(
                update: (GroupSettingsUi) -> GroupSettingsUi
            ) {
                onSettingsChanged(update(current))
            }

            SettingToggleRow(
                title = "Allow member invites",
                description = "Members can invite others to this group",
                checked = current.allowMemberInvite,
                onCheckedChange = {
                    toggleSettings { s -> s.copy(allowMemberInvite = it) }
                }
            )
            SettingToggleRow(
                title = "Allow file sharing",
                description = "Members can upload and share materials",
                checked = current.allowFileShare,
                onCheckedChange = {
                    toggleSettings { s -> s.copy(allowFileShare = it) }
                }
            )
            SettingToggleRow(
                title = "Allow chat",
                description = "Enable group chat for members",
                checked = current.allowChat,
                onCheckedChange = {
                    toggleSettings { s -> s.copy(allowChat = it) }
                }
            )
        }

        if (members.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Members",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))

            members.forEach { member ->
                MemberRoleRow(
                    member = member,
                    onRoleChange = { newRole -> onMemberRoleChange(member, newRole) },
                    onRemove = { event(GroupDetailsUiEvent.OnRemoveMember(member)) }
                )
            }
        }
    }
}

@Composable
private fun MemberRoleRow(
    member: GroupMemberUi,
    onRoleChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    val roles = listOf("admin", "moderator", "member")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "User ${member.userId}",
                style = MaterialTheme.typography.bodyMedium,
                color = TextCharcoal,
                fontWeight = FontWeight.Medium
            )
            if (member.joinedAt.isNotBlank()) {
                Text(
                    text = "Joined ${member.joinedAt}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedGray
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            roles.forEach { role ->
                val isSelected = member.role.equals(role, ignoreCase = true)
                Box(
                    modifier = Modifier
                        .background(
                            color = if (isSelected) PrimaryOlive else TextMutedGray.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { if (!isSelected) onRoleChange(role) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = role.replaceFirstChar { it.uppercaseChar() },
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isSelected) Color.White else TextCharcoal
                    )
                }
            }

            Text(
                text = "Remove",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red,
                modifier = Modifier
                    .clickable(onClick = onRemove)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextCharcoal,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedGray
            )
        }
        androidx.compose.material3.Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun MaterialsSection(
    materials: List<MaterialItem>,
    searchQuery: String,
    selectedTab: MaterialType,
    sortByLatest: Boolean,
    onSearchChange: (String) -> Unit,
    onTabChange: (MaterialType) -> Unit,
    onSortClick: () -> Unit,
    onDownload: (MaterialItem) -> Unit
) {
    Text(
        text = "Materials",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = TextCharcoal
    )
    Spacer(modifier = Modifier.height(12.dp))
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        placeholder = { Text("Search for notes, books...", color = TextMutedGray) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = TextMutedGray)
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            MaterialType.NOTES to "Notes",
            MaterialType.BOOKS to "Books",
            MaterialType.QUESTION_PAPERS to "Question Papers"
        ).forEach { (tab, label) ->
            FilterChip(
                selected = selectedTab == tab,
                onClick = { onTabChange(tab) },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White,
                    labelColor = TextMutedGray,
                    selectedContainerColor = PrimaryOlive,
                    selectedLabelColor = Color.White
                )
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${materials.size} FILES",
            style = MaterialTheme.typography.labelMedium,
            color = TextMutedGray
        )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onSortClick)
            ) {
            Text(
                text = "Sort by: ${if (sortByLatest) "Latest" else "Oldest"}",
                style = MaterialTheme.typography.labelMedium,
                color = TextMutedGray
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    materials.forEach { material ->
        MaterialItemCard(material = material, onDownload = { onDownload(material) })
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun MaterialItemCard(
    material: MaterialItem,
    onDownload: () -> Unit
) {
    val typeColor = when (material.type) {
        MaterialType.NOTES -> Color(0xFFE07A5F)
        MaterialType.BOOKS -> PrimaryOlive
        MaterialType.QUESTION_PAPERS -> Color(0xFFD4A574)
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(typeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (material.type) {
                        MaterialType.NOTES -> "PPT"
                        MaterialType.BOOKS -> "DOC"
                        MaterialType.QUESTION_PAPERS -> "Q"
                    },
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = material.title,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    maxLines = 1
                )
                Text(
                    text = buildString {
                        append("${material.sizeMb} MB")
                        material.rating?.let { append(" • $it⭐") } ?: append(" • No ratings")
                        append(" • ${material.uploaderName}")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedGray,
                    maxLines = 1
                )
            }
            IconButton(onClick = onDownload) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    tint = PrimaryOlive
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupDetailsScreenPreview() {
    val sampleGroup = GroupData(
        id = 1,
        creatorId = 100,
        name = "Advanced Calculus 101",
        groupIcon = null,
        description = null,
        groupType = "study",
        subject = "Calculus",
        maxMembers = 42,
        isPublic = 1,
        approvalRequired = 1,
        status = "active",
        lastActivityAt = null,
        inviteLink = null,
        invitationCode = null,
        createdAt = null,
        deletedAt = null
    )
    val previewState = GroupDetailsUiDataState(
        group = sampleGroup,
        memberCount = 42,
        isAdmin = true,
        joinCode = "XJ9-22B",
        unreadPendingCount = 3,
        pendingRequests = listOf(
            PendingRequestItem(
                id = "1",
                name = "Alex Smith",
                subtitle = "CS • 2nd Year"
            )
        ),
        materials = listOf(
            MaterialItem(
                id = "1",
                title = "Lecture 4 - Derivatives",
                type = MaterialType.NOTES,
                sizeMb = "3.2",
                rating = "4.8",
                uploaderName = "John Doe"
            ),
            MaterialItem(
                id = "2",
                title = "Week 2 Practice Set",
                type = MaterialType.NOTES,
                sizeMb = "1.4",
                rating = "4.5",
                uploaderName = "Sarah L."
            )
        )
    )
    StudySwapTheme {
        GroupDetailsContent(
            uiState = previewState,
            event = {},
            onPickGroupIcon = {}
        )
    }
}
