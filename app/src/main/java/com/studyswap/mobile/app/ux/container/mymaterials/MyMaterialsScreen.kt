package com.studyswap.mobile.app.ux.container.mymaterials

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
import com.studyswap.mobile.app.ui.theme.TextCharcoal
import com.studyswap.mobile.app.ui.theme.TextMutedGray
import com.studyswap.mobile.app.ux.container.marketplaceitemdetail.MarketplaceItemDetailRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMaterialsScreen(
    navController: NavController,
    viewModel: MyMaterialsViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)
    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event

    LaunchedEffect(Unit) {
        event(MyMaterialsUiEvent.Load)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = BackgroundOffWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My marketplace items",
                        fontWeight = FontWeight.ExtraBold,
                        color = TextCharcoal
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { event(MyMaterialsUiEvent.OnBackClick) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextCharcoal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundOffWhite)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.errorMessage?.let { err ->
                    item {
                        Text(text = err, color = Color(0xFFB00020), fontSize = 14.sp)
                    }
                }
                items(uiState.rows, key = { it.materialId }) { row ->
                    MyMaterialRowCard(
                        row = row,
                        expanded = row.materialId in uiState.expandedIds,
                        onToggleExpand = { event(MyMaterialsUiEvent.OnToggleExpand(row.materialId)) },
                        onOpenDetail = {
                            navController.navigate(MarketplaceItemDetailRoute.createRoute(row.materialId.toString()).value)
                        }
                    )
                }
            }
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = PrimaryOlive
                )
            }
            if (!uiState.isLoading && uiState.rows.isEmpty() && uiState.errorMessage == null) {
                Text(
                    text = "No uploaded materials yet.",
                    color = TextMutedGray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun MyMaterialRowCard(
    row: MyMaterialRowUi,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    onOpenDetail: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenDetail() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SecondaryPeach.copy(alpha = 0.25f))
                ) {
                    if (!row.thumbnail.isNullOrBlank()) {
                        AsyncImage(
                            model = row.thumbnail,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = row.title,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = listOfNotNull(row.category, "${row.purchaseCount} access").joinToString(" • "),
                        fontSize = 12.sp,
                        color = TextMutedGray
                    )
                }
                IconButton(onClick = onToggleExpand) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        tint = PrimaryOlive
                    )
                }
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = BackgroundOffWhite)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Who has access",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextCharcoal
                )
                Spacer(modifier = Modifier.height(6.dp))
                if (row.accessList.isEmpty()) {
                    Text("No redemptions yet.", color = TextMutedGray, fontSize = 13.sp)
                } else {
                    row.accessList.forEach { access ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Text(access.userName, fontWeight = FontWeight.SemiBold, color = TextCharcoal, fontSize = 14.sp)
                            access.email?.let {
                                Text(it, fontSize = 12.sp, color = PrimaryOlive)
                            }
                            Text(
                                "Granted: ${access.grantedAt}",
                                fontSize = 11.sp,
                                color = TextMutedGray
                            )
                        }
                    }
                }
            }
        }
    }
}
