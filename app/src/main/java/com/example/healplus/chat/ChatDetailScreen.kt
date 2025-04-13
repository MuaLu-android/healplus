package com.example.healplus.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.model.chat.Message
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.cart.OderTopAppBar
import kotlin.text.isNotBlank

@Composable
fun ChatDetailScreen(
    roomId: String,
    viewModel: AuthViewModel = viewModel(), // Hoặc ViewModel chat riêng
    navController: NavController
) {
    // Lắng nghe tin nhắn cho phòng chat hiện tại
    // ViewModel cần có LiveData hoặc StateFlow cho tin nhắn của phòng chat đã chọn
    val messages by viewModel.messages.collectAsState() // Giả định ViewModel có messagesForSelectedRoom LiveData
    var messageInput by remember { mutableStateOf("") } // State cho ô nhập tin nhắn

    // Gọi hàm trong ViewModel để chọn phòng chat và bắt đầu lắng nghe tin nhắn
    LaunchedEffect(roomId) {
        viewModel.selectChatRoom(roomId)
        // Có thể cần gọi hàm để tải lịch sử tin nhắn ban đầu
        // viewModel.loadMessages(roomId)
    }

    Scaffold(
        topBar = {
            // Sử dụng lại TopAppBar hoặc tạo TopAppBar mới cho màn hình chat
            // Có thể hiển thị tên người dùng đối diện hoặc ID phòng chat
            OderTopAppBar(navController = navController) // Truyền title nếu TopAppBar hỗ trợ
        },
        bottomBar = {
            // Ô nhập tin nhắn và nút gửi
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    label = { Text("Nhập tin nhắn") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(24.dp) // Bo tròn góc
                )
                IconButton(
                    onClick = {
                        if (messageInput.isNotBlank()) {
                            viewModel.sendMessage1(
                                roomId,
                                messageInput
                            ) // Gọi hàm gửi tin nhắn trong ViewModel
                            messageInput = ""
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Gửi tin nhắn"
                    )
                }
            }
        }
    ) { paddingValues ->
        // Khu vực hiển thị tin nhắn
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            reverseLayout = true // Hiển thị tin nhắn mới nhất ở dưới cùng
        ) {
            items(messages) { message ->
                // Hiển thị từng tin nhắn
                MessageItem(
                    message = message,
                    isCurrentUser = message.senderId == viewModel.getUserId()
                ) // Giả định Message có senderId
            }
        }
    }
}

@Composable
fun MessageItem(message: Message, isCurrentUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start // Căn chỉnh tin nhắn
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isCurrentUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                // Có thể hiển thị tên người gửi nếu cần
                // if (!isCurrentUser) {
                //     Text(text = message.senderName, style = MaterialTheme.typography.labelSmall)
                // }
                Text(text = message.text) // Giả định Message có thuộc tính content
                // Có thể hiển thị thời gian gửi
                // Text(text = message.timestamp, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}