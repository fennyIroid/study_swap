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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    CreateGroupContent(uiState, event)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupContent(
    uiState: CreateGroupUiDataState,
    event: (CreateGroupUiEvent) -> Unit
) {
    val scrollState = rememberScrollState()


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
