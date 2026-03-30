package com.studyswap.mobile.app.ux.container.uploadgroupfile

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadGroupFileScreen(
    navController: androidx.navigation.NavController,
    viewModel: UploadGroupFileViewModel = hiltViewModel()
) {
    HandleNavigation(viewModelNav = viewModel, navController = navController)

    val uiState by viewModel.uiState.uiDataStateFlow.collectAsStateWithLifecycle()
    val event = viewModel.uiState.event
    val context = LocalContext.current

    val resolveName: (Uri?) -> String? = { uri ->
        uri?.let {
            context.contentResolver.query(it, null, null, null, null)?.use { c ->
                val i = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (i >= 0 && c.moveToFirst()) c.getString(i) else null
            }
        }
    }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        event(UploadGroupFileUiEvent.OnFileSelected(uri, resolveName(uri)))
    }

    val thumbPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        event(UploadGroupFileUiEvent.OnThumbnailSelected(uri, resolveName(uri)))
    }

    uiState.errorMessage?.let { msg ->
        LaunchedEffect(msg) {
            kotlinx.coroutines.delay(3000)
            event(UploadGroupFileUiEvent.OnDismissError)
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.statusBarsPadding().navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Upload to group",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        modifier = Modifier.fillMaxWidth().padding(end = 48.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { event(UploadGroupFileUiEvent.OnBackClick) },
                        modifier = Modifier.padding(start = 8.dp).background(Color.White, RoundedCornerShape(50)).size(40.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextMutedGray)
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

            Text(
                text = "File",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, TextMutedGray.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.5f))
                    .clickable { filePicker.launch("*/*") },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = SecondaryPeach,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tap to attach file", color = TextCharcoal, fontSize = 14.sp)
                    uiState.selectedFileName?.let { name ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(name, color = PrimaryOlive, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Thumbnail (optional)", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, TextMutedGray.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                    .clickable { thumbPicker.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.thumbnailName ?: "Image for preview card",
                    fontSize = 13.sp,
                    color = TextMutedGray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Title", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { event(UploadGroupFileUiEvent.OnTitleChange(it)) },
                placeholder = { Text("e.g. Week 3 Notes", color = TextMutedGray.copy(alpha = 0.7f)) },
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Category", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextCharcoal)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.category,
                onValueChange = { event(UploadGroupFileUiEvent.OnCategoryChange(it)) },
                placeholder = { Text("e.g. Notes", color = TextMutedGray.copy(alpha = 0.7f)) },
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            uiState.errorMessage?.let { msg ->
                Text(msg, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = { event(UploadGroupFileUiEvent.OnUploadClick) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOlive)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Upload", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
