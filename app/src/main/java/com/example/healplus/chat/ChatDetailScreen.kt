package com.example.healplus.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.chat.Message
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.cart.OderTopAppBar
import kotlinx.coroutines.launch
import kotlin.text.isNotBlank

@Composable
fun ChatDetailScreen(
    roomId: String,
    viewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    val messages by viewModel.messages.collectAsState()
    var messageInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(roomId) {
        viewModel.selectChatRoom(roomId)
    }
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    Scaffold(
        topBar = {
            OderTopAppBar(navController = navController)
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    placeholder = { Text("Nhập tin nhắn") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
                IconButton(
                    onClick = {
                        if (messageInput.isNotBlank()) {
                            viewModel.sendMessage1(roomId, messageInput)
                            messageInput = ""
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Gửi tin nhắn",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                state = listState,
                reverseLayout = false,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages.reversed()) { message ->
                    MessageItem(
                        message = message,
                        isCurrentUser = message.senderId == viewModel.getUserId()
                    )
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, isCurrentUser: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (isCurrentUser) 60.dp else 8.dp,
                    end = if (isCurrentUser) 8.dp else 60.dp
                )
        ) {
            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isCurrentUser) 16.dp else 4.dp,
                    bottomEnd = if (isCurrentUser) 4.dp else 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrentUser)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.align(if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text(
                        text = message.text,
                        color = if (isCurrentUser)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}