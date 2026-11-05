package ru.netology.data

data class Chat(
    val chatId: Int = 0,
    val senderId: Int = 0,
    val recipientId: Int = 0,
    val messages: MutableList<Message> = mutableListOf(),
    val readOrNot: Boolean = true,
    var empty: Boolean = true
)