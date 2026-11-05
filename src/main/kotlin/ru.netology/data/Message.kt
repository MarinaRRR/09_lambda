package ru.netology.data

data class Message(
    var messageId: Int = 0,
    val text: String,
    val senderId: Int = 0,
    val recipientId: Int = 0,
    var read: Boolean = false
)