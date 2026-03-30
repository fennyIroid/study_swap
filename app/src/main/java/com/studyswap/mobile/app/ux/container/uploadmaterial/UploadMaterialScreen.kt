package com.studyswap.mobile.app.ux.container.uploadmaterial

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.studyswap.mobile.app.ui.theme.StudySwapTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.*

@Composable
fun UploadMaterialScreen(
    navController: androidx.navigation.NavController,
    viewModel: UploadMaterialViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)

    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event
    val context = LocalContext.current

    val resolveName: (Uri?) -> String? = { uri ->
        uri?.let {
            context.contentResolver.query(it, null, null, null, null)?.use { c ->
                val i = c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (i >= 0 && c.moveToFirst()) c.getString(i) else null
            }
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        event(UploadMaterialUiEvent.OnFileSelected(uri, resolveName(uri)))
    }

    val thumbPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        event(UploadMaterialUiEvent.OnThumbnailSelected(uri, resolveName(uri)))
    }

    LaunchedEffect(Unit) {
        event(UploadMaterialUiEvent.Load)
    }

    UploadMaterialContent(
        uiState = uiState,
        event = event,
        onPickFile = { filePickerLauncher.launch("*/*") },
        onPickThumbnail = { thumbPickerLauncher.launch("image/*") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadMaterialContent(
    uiState: UploadMaterialUiDataState,
    event: (UploadMaterialUiEvent) -> Unit,
    onPickFile: () -> Unit,
    onPickThumbnail: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    uiState.errorMessage?.let { msg ->
        LaunchedEffect(msg) {
            kotlinx.coroutines.delay(3000)
            event(UploadMaterialUiEvent.OnDismissError)
        }
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding().navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Upload Material",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        modifier = Modifier.fillMaxWidth().padding(end = 48.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { event(UploadMaterialUiEvent.OnBackClick) },
                        modifier = Modifier.padding(start = 8.dp).background(Color.White, RoundedCornerShape(50)).size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextMutedGray
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
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Upload Document
            Text(
                text = "Upload Document",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 2.dp,
                        color = TextMutedGray.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Color.White.copy(alpha = 0.5f))
                    .clickable(onClick = onPickFile),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = SecondaryPeach,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Click to upload or drag and drop",
                        color = TextCharcoal,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "PDF, DOC, PPT, ZIP (max 50MB)",
                        color = TextMutedGray,
                        fontSize = 12.sp
                    )
                    uiState.selectedFileName?.let { name ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = name,
                            color = PrimaryOlive,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Material Title
            Text(
                text = "Material Title",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.materialTitle,
                onValueChange = { event(UploadMaterialUiEvent.OnMaterialTitleChange(it)) },
                placeholder = { Text("e.g., Biology 101 Finals Notes", color = TextMutedGray.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                    unfocusedTextColor = TextCharcoal,
                    focusedTextColor = TextCharcoal
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Category
            Text(
                text = "Category",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.category,
                onValueChange = { event(UploadMaterialUiEvent.OnCategoryChange(it)) },
                placeholder = { Text("e.g. Computer Science", color = TextMutedGray.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                    unfocusedTextColor = TextCharcoal,
                    focusedTextColor = TextCharcoal
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            if (uiState.suggestedCategories.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Suggestions",
                    fontSize = 12.sp,
                    color = TextMutedGray,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.suggestedCategories) { cat ->
                        FilterChip(
                            selected = false,
                            onClick = { event(UploadMaterialUiEvent.OnSuggestionPick(cat)) },
                            label = { Text(cat, maxLines = 1, fontSize = 12.sp) },
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = TextMutedGray.copy(alpha = 0.3f),
                                selectedBorderColor = Color.Transparent,
                                enabled = true,
                                selected = false
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Description",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { event(UploadMaterialUiEvent.OnDescriptionChange(it)) },
                placeholder = { Text("Describe what buyers will get", color = TextMutedGray.copy(alpha = 0.7f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                    unfocusedTextColor = TextCharcoal,
                    focusedTextColor = TextCharcoal
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Price (optional)",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.price,
                onValueChange = { event(UploadMaterialUiEvent.OnPriceChange(it)) },
                placeholder = { Text("0.00 — leave empty for free", color = TextMutedGray.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                    unfocusedTextColor = TextCharcoal,
                    focusedTextColor = TextCharcoal
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Thumbnail (optional)",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, TextMutedGray.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.5f))
                    .clickable(onClick = onPickThumbnail),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.thumbnailName ?: "Tap to add JPG / PNG (max 5MB)",
                    color = TextMutedGray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Upload Material button
            Button(
                onClick = { event(UploadMaterialUiEvent.OnUploadClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOlive),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Upload Material",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, name = "Upload Material", device = "spec:width=411dp,height=891dp")
@Composable
private fun UploadMaterialScreenPreview() {
    StudySwapTheme {
        UploadMaterialContent(
            uiState = UploadMaterialUiDataState(),
            event = {},
            onPickFile = {},
            onPickThumbnail = {}
        )
    }
}
