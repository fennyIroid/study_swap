package com.studyswap.mobile.app.ux.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.studyswap.mobile.app.ui.compose.common.BottomNavigationBar
import com.studyswap.mobile.app.ui.theme.*
import com.studyswap.mobile.app.ux.container.marketplaceitemdetail.MarketplaceItemDetailRoute
import com.studyswap.mobile.app.ux.container.uploadmaterial.UploadMaterialRoute
import com.studyswap.mobile.app.ux.main.marketplace.MarketplaceRoute

@Composable
fun HomeScreen(
    navController: NavController? = null,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding() // 👈 Added status bar padding
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp) // Adjusted for nav bar
        ) {
            // 1. Top Bar
            item {
                HomeTopBar()
            }

            // 2. Greeting
            item {
                GreetingSection(userName = uiState.userName)
            }

            // 3. Quick Action Cards
            item {
                QuickActionSection(
                    onUploadClick = { navController?.navigate(UploadMaterialRoute.routeDefinition.value) },
                    onFindClick = { navController?.navigate(MarketplaceRoute.routeDefinition.value) }
                )
            }

            // 4. Continue Learning
            item {
                SectionHeader(title = "Continue Learning", actionLabel = "View all")
            }
            items(uiState.recentMaterials) { material ->
                ContinueLearningCard(
                    material = material,
                    onClick = { /* Navigate to material detail when implemented */ }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // 5. Trending in Marketplace
            item {
                SectionHeader(
                    title = "Trending in Marketplace", 
                    actionLabel = "Explore",
                    onActionClick = { navController?.navigate(MarketplaceRoute.routeDefinition.value) }
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.trendingProducts.forEach { product ->
                        TrendingProductCard(
                            modifier = Modifier.weight(1f), 
                            product = product,
                            onClick = { 
                                navController?.navigate(MarketplaceItemDetailRoute.createRoute(product.id.toString()).value)
                            }
                        )
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        // Floating bottom nav bar overlay
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar(navController)
        }
    }
}

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(SecondaryPeach.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://ui-avatars.com/api/?name=Alex&background=F4BC96&color=fff",
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Logo Text
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Study",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFE67E22), // Vibrant Orange
                    letterSpacing = 0.5.sp
                )
            )
            Text(
                text = "Swap",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryOlive,
                    letterSpacing = 0.5.sp
                )
            )
        }

        // Icons - Removed Search icon as requested
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(BackgroundOffWhite, CircleShape)
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = TextCharcoal, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun GreetingSection(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Hi, $userName!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1A1A1A)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "👋", fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ready to swap some knowledge today?",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun QuickActionSection(onUploadClick: () -> Unit, onFindClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upload Card
        Card(
            modifier = Modifier
                .weight(1f)
                .height(160.dp)
                .clickable { onUploadClick() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF2E8))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFFF8C42), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.FileUpload, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Upload", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextCharcoal)
                Text(text = "Share your notes", fontSize = 12.sp, color = Color.Gray)
            }
        }

        // Find Materials Card
        Card(
            modifier = Modifier
                .weight(1f)
                .height(160.dp)
                .clickable { onFindClick() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F6F1))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(PrimaryOlive, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Search, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Find Materials", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextCharcoal)
                Text(text = "Explore resources", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, actionLabel: String, onActionClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
        )
        Text(
            text = actionLabel,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFFE67E22),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}

@Composable
private fun ContinueLearningCard(material: RecentMaterial, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail placeholder
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BackgroundOffWhite),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📄", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = material.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextCharcoal
                )
                Text(
                    text = "${material.department} • ${material.pageCount} pages",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                // Progress Bar
                LinearProgressIndicator(
                    progress = { material.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = Color(0xFFE67E22),
                    trackColor = BackgroundOffWhite
                )
            }
        }
    }
}

@Composable
private fun TrendingProductCard(modifier: Modifier = Modifier, product: TrendingProduct, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(260.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(BackgroundOffWhite),
                contentAlignment = Alignment.TopEnd
            ) {
                // Image or Placeholder
                Text(text = "📚", fontSize = 48.sp, modifier = Modifier.align(Alignment.Center))
                
                // Rating Badge
                Surface(
                    modifier = Modifier.padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.9f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "⭐", fontSize = 10.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = product.rating.toString(), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
            
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextCharcoal,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$${"%.2f".format(product.price)}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color(0xFFE67E22)
                )
            }
        }
    }
}
