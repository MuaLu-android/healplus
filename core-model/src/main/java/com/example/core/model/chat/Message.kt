package com.example.core.model.chat

data class Message(
    val senderId: String = "",
    val text: String = "",
    val timestamp: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
)
