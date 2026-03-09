package com.studyswap.mobile.app.ux.container.joingroup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.studyswap.mobile.app.data.source.remote.model.GroupData
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray
import androidx.compose.ui.tooling.preview.Preview
import com.studyswap.mobile.app.ui.theme.StudySwapTheme

private val joinGroupCategories = listOf(
    "All Groups", "Calculus", "Biology", "Literature", "Chemistry", "History"
)

@Composable
fun JoinGroupScreen(
    navController: NavController,
    viewModel: JoinGroupViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)

    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    LaunchedEffect(Unit) {
        event(JoinGroupUiEvent.LoadGroups)
    }

    JoinGroupContent(uiState = uiState, event = event)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JoinGroupContent(
    uiState: JoinGroupUiDataState,
    event: (JoinGroupUiEvent) -> Unit
) {
    val filteredGroups = remember(
        uiState.browseGroups,
        uiState.selectedCategory,
        uiState.searchQuery
    ) {
        var list = uiState.browseGroups
        if (uiState.selectedCategory != "All Groups") {
            list = list.filter { it.subject.equals(uiState.selectedCategory, true) }
        }
        val q = uiState.searchQuery.trim().lowercase()
        if (q.isNotEmpty()) {
            list = list.filter { g ->
                g.name?.lowercase()?.contains(q) == true ||
                    g.subject?.lowercase()?.contains(q) == true ||
                    g.groupType?.lowercase()?.contains(q) == true
            }
        }
        list
    }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Join Group",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { event(JoinGroupUiEvent.OnBackClick) },
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White, CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextCharcoal
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundOffWhite)
            )
        },
        containerColor = BackgroundOffWhite
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Join by Invite Code section
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(PrimaryOlive.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryOlive,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Join a Study Group",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Connect with classmates and start sharing materials instantly.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMutedGray
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.inviteCode,
                onValueChange = { event(JoinGroupUiEvent.OnInviteCodeChange(it)) },
                label = { Text("Group Invite Code") },
                placeholder = { Text("e.g. SWAP-2023") },
                trailingIcon = {
                    IconButton(onClick = { event(JoinGroupUiEvent.OnPasteOrQrClick) }) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Paste or scan",
                            tint = TextMutedGray
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = TextMutedGray.copy(alpha = 0.5f),
                    unfocusedBorderColor = TextMutedGray.copy(alpha = 0.3f),
                    focusedLabelColor = TextMutedGray,
                    unfocusedLabelColor = TextMutedGray
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { event(JoinGroupUiEvent.OnJoinWithCodeClick) },
                enabled = !uiState.isJoinByCodeLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOlive),
                contentPadding = PaddingValues(0.dp)
            ) {
                if (uiState.isJoinByCodeLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Join with Code", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "OR BROWSE GROUPS",
                style = MaterialTheme.typography.labelSmall,
                color = TextMutedGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Explore Communities
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Explore Communities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
                Text(
                    text = "View all",
                    style = MaterialTheme.typography.bodySmall,
                    color = PrimaryOlive,
                    modifier = Modifier.clickable { event(JoinGroupUiEvent.OnViewAllClick) }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { event(JoinGroupUiEvent.OnSearchChange(it)) },
                placeholder = { Text("Search by subject, class, or topic", color = TextMutedGray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = TextMutedGray,
                        modifier = Modifier.size(20.dp)
                    )
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

            // Category filter chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(joinGroupCategories) { category ->
                    val isSelected = category == uiState.selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { event(JoinGroupUiEvent.OnCategoryChange(category)) },
                        label = {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        shape = RoundedCornerShape(24.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            labelColor = TextMutedGray,
                            selectedContainerColor = PrimaryOlive,
                            selectedLabelColor = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color.Transparent,
                            selectedBorderColor = Color.Transparent,
                            enabled = true,
                            selected = isSelected
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryOlive)
                }
            } else {
                filteredGroups.forEach { group ->
                    JoinGroupBrowseCard(
                        group = group,
                        isRequested = group.id in uiState.requestedGroupIds,
                        onRequestClick = { event(JoinGroupUiEvent.OnRequestJoin(group)) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Create new group card (dashed border)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 2.dp,
                        color = PrimaryOlive.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { event(JoinGroupUiEvent.OnCreateGroupClick) }
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = PrimaryOlive,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Can't find your class?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextCharcoal
                    )
                    Text(
                        text = "Create a new group",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryOlive
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun JoinGroupBrowseCard(
    group: GroupData,
    isRequested: Boolean,
    onRequestClick: () -> Unit
) {
    val iconColor = when ((group.id ?: 0) % 3) {
        0 -> PrimaryOlive
        1 -> SecondaryPeach
        else -> Color(0xFFE07A5F)
    }
    val iconInitial = group.name?.firstOrNull()?.uppercaseChar()?.toString() ?: "G"

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                group.groupIcon?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(14.dp)),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(iconColor.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = iconInitial,
                            color = iconColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = group.name ?: "Unnamed Group",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = TextMutedGray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${group.maxMembers ?: "?"} Members",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGray
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = TextMutedGray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = formatLastActive(group.lastActivityAt),
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGray
                        )
                    }
                }
            }
            Button(
                onClick = onRequestClick,
                enabled = !isRequested,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRequested) TextMutedGray.copy(alpha = 0.2f) else PrimaryOlive,
                    contentColor = if (isRequested) TextMutedGray else Color.White
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = if (isRequested) "Requested" else "Request",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun formatLastActive(lastActivityAt: String?): String {
    if (lastActivityAt.isNullOrBlank()) return "Active recently"
    return "Active ${lastActivityAt.take(20)}"
}


@Preview(showBackground = true)
@Composable
fun JoinGroupScreenPreview() {
    val sampleGroups = listOf(
        GroupData(
            id = 1,
            creatorId = 100,
            name = "Advanced Calc 101",
            groupIcon = null,
            description = "Study group for calculus",
            groupType = "study",
            subject = "Calculus",
            maxMembers = 12,
            isPublic = 1,
            approvalRequired = 1,
            status = "active",
            lastActivityAt = "2025-03-09T10:00:00",
            inviteLink = null,
            createdAt = null,
            deletedAt = null
        ),
        GroupData(
            id = 2,
            creatorId = 101,
            name = "Literature Club",
            groupIcon = null,
            description = null,
            groupType = "study",
            subject = "Literature",
            maxMembers = 45,
            isPublic = 1,
            approvalRequired = 0,
            status = "active",
            lastActivityAt = "2025-03-09T09:00:00",
            inviteLink = null,
            createdAt = null,
            deletedAt = null
        ),
        GroupData(
            id = 3,
            creatorId = 102,
            name = "Organic Chem Lab",
            groupIcon = null,
            description = null,
            groupType = "study",
            subject = "Chemistry",
            maxMembers = 8,
            isPublic = 1,
            approvalRequired = 1,
            status = "active",
            lastActivityAt = "2025-03-08T14:30:00",
            inviteLink = null,
            createdAt = null,
            deletedAt = null
        )
    )
    val previewState = JoinGroupUiDataState(
        inviteCode = "",
        browseGroups = sampleGroups,
        selectedCategory = "All Groups",
        searchQuery = "",
        requestedGroupIds = setOf(3),
        isLoading = false,
        isJoinByCodeLoading = false,
        errorMessage = null
    )
    StudySwapTheme {
        JoinGroupContent(
            uiState = previewState,
            event = {}
        )
    }
}