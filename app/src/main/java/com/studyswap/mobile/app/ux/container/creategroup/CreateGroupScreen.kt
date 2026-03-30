package com.studyswap.mobile.app.ux.container.creategroup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.provider.OpenableColumns
import coil.compose.AsyncImage
import com.studyswap.mobile.app.navigation.HandleNavigation
import com.studyswap.mobile.app.ui.theme.*

@Composable
fun CreateGroupScreen(
    navController: androidx.navigation.NavController,
    viewModel: CreateGroupViewModel = hiltViewModel()
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

    val iconPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        event(CreateGroupUiEvent.OnGroupIconSelected(uri, resolveName(uri)))
    }

    CreateGroupContent(
        uiState = uiState,
        event = event,
        onPickGroupIcon = { iconPicker.launch("image/*") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupContent(
    uiState: CreateGroupUiDataState,
    event: (CreateGroupUiEvent) -> Unit,
    onPickGroupIcon: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    uiState.invitationCode?.let { code ->
        AlertDialog(
            onDismissRequest = { event(CreateGroupUiEvent.OnDismissInviteDialog) },
            title = {
                Text(
                    text = "Group created",
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            },
            text = {
                Column {
                    Text(
                        text = "Share this 6-character invite code with members:",
                        color = TextMutedGray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = code,
                        fontWeight = FontWeight.Black,
                        fontSize = 22.sp,
                        color = PrimaryOlive
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { event(CreateGroupUiEvent.OnDismissInviteDialog) }) {
                    Text("Done", color = PrimaryOlive, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = Color.White
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Group",
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal,
                        modifier = Modifier.fillMaxWidth().padding(end = 48.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { event(CreateGroupUiEvent.OnBackClick) },
                        modifier = Modifier.padding(start = 8.dp).background(Color.White, CircleShape).size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
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
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            FormSectionLabel("GROUP ICON (OPTIONAL)")
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, TextMutedGray.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                    .clickable { onPickGroupIcon() },
                contentAlignment = Alignment.Center
            ) {
                val iconUri = uiState.groupIconUri
                if (iconUri != null) {
                    AsyncImage(
                        model = iconUri,
                        contentDescription = "Group icon",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Add group icon",
                        tint = TextMutedGray,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            if (uiState.groupIconUri != null) {
                TextButton(onClick = { event(CreateGroupUiEvent.OnClearGroupIcon) }) {
                    Text("Remove icon", color = PrimaryOlive, fontSize = 13.sp)
                }
            }
            uiState.groupIconName?.let { name ->
                Text(
                    text = name,
                    fontSize = 12.sp,
                    color = TextMutedGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Form Fields
            FormSectionLabel("GROUP NAME")
            OutlinedTextField(
                value = uiState.groupName,
                onValueChange = { event(CreateGroupUiEvent.OnGroupNameChange(it)) },
                placeholder = { Text("e.g., Advanced Calculus 101", color = TextMutedGray.copy(alpha=0.5f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormSectionLabel("SUBJECT")
            OutlinedTextField(
                value = uiState.subject,
                onValueChange = { event(CreateGroupUiEvent.OnSubjectChange(it)) },
                placeholder = { Text("Select Subject", color = TextCharcoal) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown", tint = PrimaryOlive)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormSectionLabel("MAX MEMBERS")
            OutlinedTextField(
                value = uiState.maxMembers,
                onValueChange = { event(CreateGroupUiEvent.OnMaxMembersChange(it)) },
                placeholder = { Text("e.g., 10", color = TextMutedGray.copy(alpha=0.5f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "DESCRIPTION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextMutedGray,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "(optional)",
                    fontSize = 12.sp,
                    color = TextMutedGray.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { event(CreateGroupUiEvent.OnDescriptionChange(it)) },
                placeholder = { Text("What is this group about?", color = TextMutedGray.copy(alpha=0.5f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = PrimaryOlive,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))



            Button(
                onClick = { event(CreateGroupUiEvent.OnCreateClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOlive)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Create Group",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Arrow")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.errorMessage != null) {
                Text(text = uiState.errorMessage, color = Color.Red, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FormSectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextMutedGray,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        letterSpacing = 1.sp
    )
}
