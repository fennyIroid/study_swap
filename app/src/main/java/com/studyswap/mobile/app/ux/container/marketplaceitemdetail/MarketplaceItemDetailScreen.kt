package com.studyswap.mobile.app.ux.container.marketplaceitemdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray

@Composable
fun MarketplaceItemDetailScreen(
    navController: NavController,
    viewModel: MarketplaceItemDetailViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)

    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    LaunchedEffect(Unit) {
        event(MarketplaceItemDetailUiEvent.Load)
    }

    MarketplaceItemDetailContent(uiState = uiState, event = event)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarketplaceItemDetailContent(
    uiState: MarketplaceItemDetailUiDataState,
    event: (MarketplaceItemDetailUiEvent) -> Unit
) {
    val item = uiState.item

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { event(MarketplaceItemDetailUiEvent.OnBackClick) },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(Color.White, CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextCharcoal)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { event(MarketplaceItemDetailUiEvent.OnBookmarkToggle) },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(Color.White, CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (uiState.isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
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
                .padding(horizontal = 20.dp)
        ) {
            // Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(22.dp))
            ) {
                val coverUrl = item?.coverUrl
                if (!coverUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = coverUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFD7A26F),
                                        SecondaryPeach,
                                        Color(0xFF6E7D61)
                                    )
                                )
                            )
                    )
                }

                IconButton(
                    onClick = { event(MarketplaceItemDetailUiEvent.OnCameraClick) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color.White, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = "Camera", tint = SecondaryPeach)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = item?.title ?: "",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = TextCharcoal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Author
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(TextMutedGray.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item?.authorName?.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                            fontWeight = FontWeight.Bold,
                            color = TextMutedGray
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = item?.authorSubtitle ?: "AUTHOR",
                            fontSize = 10.sp,
                            color = TextMutedGray,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item?.authorName ?: "",
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    }
                }

                // Rating pill
                Card(
                    colors = CardDefaults.cardColors(containerColor = SecondaryPeach.copy(alpha = 0.25f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "★ ${item?.rating ?: 0.0}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB66B2E)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "(${item?.ratingCount ?: 0})",
                            color = TextMutedGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Small info chips
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                InfoChip(text = "${item?.semesterLabel ?: ""}")
                if (item?.formats?.contains(MarketplaceItemFormat.PDF) == true) {
                    InfoChip(text = "PDF")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item?.description ?: "",
                color = TextMutedGray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { event(MarketplaceItemDetailUiEvent.OnDownloadClick) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOlive),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(Icons.Default.Download, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Download Material", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // File card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SecondaryPeach.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PDF",
                            fontWeight = FontWeight.Bold,
                            color = SecondaryPeach
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item?.fileName ?: "",
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = item?.fileMetaLine ?: "",
                            color = TextMutedGray,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(onClick = { event(MarketplaceItemDetailUiEvent.OnFileMoreClick) }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = TextMutedGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
private fun InfoChip(text: String) {
    Box(
        modifier = Modifier
            .border(1.dp, TextMutedGray.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
            .background(Color.White, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = text, color = TextCharcoal, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
private fun MarketplaceItemDetailPreview() {
    val previewState = MarketplaceItemDetailUiDataState(
        item = MarketplaceItemDetail(
            id = "macro",
            title = "Intro to\nMacroeconomics Notes",
            authorName = "Sarah J.",
            authorSubtitle = "AUTHOR",
            rating = 4.8,
            ratingCount = 120,
            semesterLabel = "Spring '23",
            description = "Comprehensive notes covering chapters 1–5 of the standard curriculum.\n\nThese notes include detailed graphs for supply and demand and clear worked examples.",
            formats = listOf(MarketplaceItemFormat.NOTES, MarketplaceItemFormat.PDF),
            coverUrl = null,
            fileName = "Macro_Intro_Final.pdf",
            fileMetaLine = "2.4 MB • 14 PAGES • HIGH RES"
        ),
        isBookmarked = false
    )

    StudySwapTheme {
        MarketplaceItemDetailContent(uiState = previewState, event = {})
    }
}
