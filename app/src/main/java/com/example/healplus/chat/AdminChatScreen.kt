package com.example.healplus.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.viewmodel.authviewmodel.AuthViewModel
import com.example.healplus.cart.OderTopAppBar

@Composable
fun AdminChatScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    val adminChatRooms by viewModel.adminChatRoomsLiveData.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            OderTopAppBar(navController)
        }
    ) {paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            LazyColumn(modifier = modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
            ){
                items(adminChatRooms) { roomId ->
                    AdminChatRoomItem(roomId = roomId, navController = navController)
                }
            }
        }
    }
}
@Composable
fun AdminChatRoomItem(roomId: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("chat_detail/${roomId}") }
            .padding(16.dp)
    ) {
        Text(text = "Chat Room: $roomId")
        Divider()
    }
}