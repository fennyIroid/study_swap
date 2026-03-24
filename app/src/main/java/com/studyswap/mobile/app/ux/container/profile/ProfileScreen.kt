package com.studyswap.mobile.app.ux.container.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyswap.mobile.app.BuildConfig

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.*
import com.studyswap.mobile.app.ui.compose.common.BottomNavigationBar

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)
    
    val uiStateData by viewModel.uiState.profileUiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    ProfileScreenContent(uiStateData = uiStateData, event = event, navController = navController)
}

@Composable
private fun ProfileScreenContent(
    uiStateData: ProfileUiDataState,
    event: (ProfileUiEvent) -> Unit,
    navController: NavController? = null
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Header with Settings
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextCharcoal
                    )
                )
                IconButton(
                    onClick = { event(ProfileUiEvent.OnSettingsClick) },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = TextCharcoal
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Profile Image & Info
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(140.dp),
                    shape = CircleShape,
                    border = CardDefaults.outlinedCardBorder(enabled = true),
                    color = Color.LightGray
                ) {
                    val imageUrl = uiStateData.userData?.profileImage
                    if (!imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = uiStateData.userData?.fullName?.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }
                
                // Verified Badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White, CircleShape)
                        .padding(2.dp)
                        .background(Color(0xFF81C784), CircleShape), // Green verified
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = uiStateData.userData?.fullName ?: "User name",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                )
            )
            Text(
                text = uiStateData.userData?.email ?: "User email",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextMutedGray
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Badges
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileBadge(
                    text = uiStateData.userData?.university ?: "Stanford University",
                    icon = Icons.Default.School,
                    containerColor = SecondaryPeach.copy(alpha = 0.15f),
                    contentColor = SecondaryPeach
                )
                ProfileBadge(
                    text = uiStateData.userData?.major ?: "CS Major",
                    icon = Icons.Default.Code,
                    containerColor = Color(0xFFE1F2E8), // Mint
                    contentColor = PrimaryOlive
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Stats Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = uiStateData.userData?.uploads?.toString() ?: "12",
                    label = "UPLOADS"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = uiStateData.userData?.rating?.toString() ?: "4.8",
                    label = "RATING",
                    hasStar = true
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = uiStateData.userData?.reputation?.toString() ?: "156",
                    label = "REPUTATION"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. Menu List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(vertical = 8.dp)
            ) {
                ProfileMenuItem(
                    icon = Icons.Default.Edit,
                    label = "Edit Profile",
                    containerColor = Color(0xFFF1F4F0), // Soft mint/gray
                    iconTint = PrimaryOlive,
                    onClick = { event(ProfileUiEvent.OnEditProfileClick) }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = BackgroundOffWhite)
                ProfileMenuItem(
                    icon = Icons.Default.FileUpload,
                    label = "Uploaded Materials",
                    containerColor = SecondaryPeach.copy(alpha = 0.15f),
                    iconTint = SecondaryPeach,
                    onClick = { event(ProfileUiEvent.OnUploadedMaterialsClick) }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = BackgroundOffWhite)
                ProfileMenuItem(
                    icon = Icons.Outlined.BookmarkBorder,
                    label = "Saved Materials",
                    containerColor = Color(0xFFF1F4F0),
                    iconTint = PrimaryOlive,
                    onClick = { event(ProfileUiEvent.OnSavedMaterialsClick) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Help & Support
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color.White
            ) {
                ProfileMenuItem(
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    label = "Help & Support",
                    containerColor = Color(0xFFF1F4F0),
                    iconTint = TextCharcoal,
                    onClick = { event(ProfileUiEvent.OnHelpSupportClick) }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            
            Text(
                text = "Version 2.4.0",
                style = MaterialTheme.typography.bodySmall.copy(color = TextMutedGray),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        if (uiStateData.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryOlive)
            }
        }

        // Floating bottom nav bar overlay
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomNavigationBar(navController)
        }
    }
}

@Composable
private fun ProfileBadge(
    text: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color
) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    hasStar: Boolean = false
) {
    Surface(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextCharcoal
                    )
                )
                if (hasStar) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = SecondaryPeach,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMutedGray,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    containerColor: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(containerColor, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    StudySwapTheme {
        ProfileScreenContent(
            uiStateData = ProfileUiDataState(userData = null),
            event = {}
        )
    }
}
