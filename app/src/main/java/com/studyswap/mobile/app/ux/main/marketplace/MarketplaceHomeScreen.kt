package com.studyswap.mobile.app.ux.main.marketplace

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.studyswap.mobile.app.ux.container.marketplaceitemdetail.MarketplaceItemDetailRoute
import com.example.studyswap.ui.components.BottomNavigationBar
import com.studyswap.mobile.app.ui.theme.BackgroundOffWhite
import com.studyswap.mobile.app.ui.theme.PrimaryOlive
import com.studyswap.mobile.app.ui.theme.SecondaryPeach
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray

@Composable
fun MarketplaceHomeScreen(
    navController: NavController? = null,
    viewModel: MarketplaceHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    LaunchedEffect(Unit) {
        event(MarketplaceHomeUiEvent.Load)
    }

    MarketplaceHomeContent(uiState = uiState, event = event, navController = navController)
}

@Composable
private fun MarketplaceHomeContent(
    uiState: MarketplaceHomeUiDataState,
    event: (MarketplaceHomeUiEvent) -> Unit,
    navController: NavController? = null
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = BackgroundOffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 8.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Header(uiState = uiState, onNotifications = { event(MarketplaceHomeUiEvent.OnNotificationsClick) })
            }

            item { Spacer(modifier = Modifier.height(14.dp)) }

            item {
                SearchRow(
                    value = uiState.searchQuery,
                    onValueChange = { event(MarketplaceHomeUiEvent.OnSearchChange(it)) }
                )
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                CategoryChips(
                    selected = uiState.selectedCategory,
                    onSelect = { event(MarketplaceHomeUiEvent.OnCategorySelected(it)) }
                )
            }

            item { Spacer(modifier = Modifier.height(18.dp)) }

            item {
                SectionHeader(
                    title = "Trending Notes",
                    onSeeAll = { event(MarketplaceHomeUiEvent.OnTrendingSeeAll) }
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.trendingNotes) { note ->
                        TrendingCard(note = note, onClick = {
                            navController?.navigate(MarketplaceItemDetailRoute.createRoute(note.id).value)
                            event(MarketplaceHomeUiEvent.OnTrendingClick(note.id))
                        })
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(18.dp)) }

            item {
                Text(
                    text = "Fresh Materials",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                val rows = (uiState.freshMaterials.size + 1) / 2
                val gridHeight = (rows * 210).dp
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    userScrollEnabled = false,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(gridHeight)
                ) {
                    items(uiState.freshMaterials) { item ->
                        FreshCard(item = item, onClick = {
                            navController?.navigate(MarketplaceItemDetailRoute.createRoute(item.id).value)
                            event(MarketplaceHomeUiEvent.OnFreshClick(item.id))
                        })
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(18.dp)) }
        }
    }
}

@Composable
private fun Header(uiState: MarketplaceHomeUiDataState, onNotifications: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SecondaryPeach.copy(alpha = 0.45f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.userName.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Hi!",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedGray
                )
                Text(
                    text = "${uiState.userName} ${uiState.greetingEmoji}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            }
        }
        IconButton(
            onClick = onNotifications,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(40.dp)
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = TextMutedGray)
        }
    }
}

@Composable
private fun SearchRow(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Find notes, books, guides…", color = TextMutedGray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextMutedGray) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                tint = TextMutedGray,
                modifier = Modifier.padding(end = 8.dp)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
}

@Composable
private fun CategoryChips(selected: MarketplaceCategory, onSelect: (MarketplaceCategory) -> Unit) {
    val chips = listOf(
        MarketplaceCategory.COMPUTER_SCIENCE to "Computer Science",
        MarketplaceCategory.MATH to "Math",
        MarketplaceCategory.HISTORY to "History"
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(chips) { (cat, label) ->
            val isSelected = cat == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(cat) },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
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
}

@Composable
private fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextCharcoal
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.bodySmall,
            color = PrimaryOlive,
            modifier = Modifier.clickable(onClick = onSeeAll)
        )
    }
}

@Composable
private fun TrendingCard(note: TrendingNoteItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color(0xFFE9E9E9)),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = note.tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = note.subtitle,
                    color = TextMutedGray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${"%.2f".format(note.price)}",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "${note.rating}★",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDAA520)
                    )
                }
            }
        }
    }
}

@Composable
private fun FreshCard(item: FreshMaterialItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFE9E9E9)),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = item.tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.subtitle,
                    color = TextMutedGray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${"%.2f".format(item.price)}",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "${item.rating}★",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDAA520)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarketplaceHomePreview() {
    val previewState = MarketplaceHomeUiDataState(
        userName = "Alex Student",
        searchQuery = "",
        selectedCategory = MarketplaceCategory.COMPUTER_SCIENCE,
        trendingNotes = listOf(
            TrendingNoteItem(
                id = "t1",
                title = "Advanced Calculus",
                subtitle = "Finals Prep 2024",
                rating = 4.9,
                price = 5.0,
                tag = "CALC"
            ),
            TrendingNoteItem(
                id = "t2",
                title = "Organic Chem",
                subtitle = "Midterm Pack",
                rating = 4.8,
                price = 6.0,
                tag = "CHEM"
            )
        ),
        freshMaterials = listOf(
            FreshMaterialItem(
                id = "f1",
                title = "Intro to Algorithms",
                subtitle = "Data Structures",
                rating = 4.6,
                price = 12.0,
                tag = "CS112"
            ),
            FreshMaterialItem(
                id = "f2",
                title = "Organic Chemistry",
                subtitle = "Midterm Prep",
                rating = 4.8,
                price = 12.0,
                tag = "CHEM201"
            ),
            FreshMaterialItem(
                id = "f3",
                title = "Modern Art History",
                subtitle = "Summaries",
                rating = 5.0,
                price = 3.5,
                tag = "ART100"
            ),
            FreshMaterialItem(
                id = "f4",
                title = "Corporate Finance",
                subtitle = "Case Studies",
                rating = 5.0,
                price = 3.99,
                tag = "MKT201"
            )
        )
    )
    StudySwapTheme {
        MarketplaceHomeContent(uiState = previewState, event = {}, navController = null)
    }
}
