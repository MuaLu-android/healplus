package com.example.healplus.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.chat.Message
import com.example.core.viewmodel.authviewmodel.AuthViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserChatScreen(viewModel: AuthViewModel = viewModel(), modifier: Modifier = Modifier) {
    val messages by viewModel.messages.collectAsState()
    val currentUserId = viewModel.getUserId()?: ""
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Trung tâm hỗ trợ",
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages.size) { index ->
                    val message = messages[index]
                    ChatBubble(message, currentUserId)
                }
            }

            UserInput(onMessageSent = { text ->
                viewModel.sendMessage(text)
            })
        }
    }
}

@Composable
fun ChatBubble(message: Message, currentUserId: String) {
    val isUser = message.senderId == currentUserId
    val alignment = if (isUser) Alignment.TopEnd else Alignment.TopStart

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .align(alignment)
                .background(Color.Blue,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = message.text,
            )
        }
    }
}

@Composable
fun UserInput(onMessageSent: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(
                    "Gửi yêu cầu",
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true
        )
        IconButton(
            onClick = {
                if (text.isNotBlank()) {
                    onMessageSent(text)
                    text = ""
                }
            },
            modifier = Modifier
                .size(48.dp)
        ) {
            Icon(
                Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
