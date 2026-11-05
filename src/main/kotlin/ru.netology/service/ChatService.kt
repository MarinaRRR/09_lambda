package ru.netology.service

import ru.netology.data.Chat
import ru.netology.data.Message
import ru.netology.exeptions.NotFoundException

object ChatService{
    private var chats = mutableListOf<Chat>()
    private var chatId = 0
    private var messageId = 0

    fun clear(){
        chats = mutableListOf()
        chatId = 0
        messageId = 0
    }

    fun getChats(id: Int): List<Chat> {
        return chats.filter { it.senderId == id || it.recipientId == id }
    }

    fun getUnreadChatsCount(userId: Int): Int {
        return chats.filter { (it.senderId == userId || it.recipientId == userId) && it.readOrNot }.size
    }

    fun getLastMessage(userId: Int): List<String> {
        val chats = chats.filter { (it.senderId == userId || it.recipientId == userId)}
        val message = mutableListOf<String>()
        chats.forEach {
            if (it.empty)
                message.add("Нет сообщений")
            else{
                val lastMessage = it.messages.takeLast(1)
                message.add(lastMessage[0].text)
            }
        }
        return message
    }

    fun getMessage(senderId: Int, chatId: Int, count: Int): List<Message> {
        val chat = chats.find { (it.senderId == senderId || it.recipientId == senderId) && it.chatId == chatId} ?: throw NotFoundException("Чат не найден")
        val message = chat.messages.takeLast(count)
        message.forEach { it.read = true }
        return message
    }

    fun sendMessage(senderId: Int, recipientId: Int, text: String) {
        val message = Message(messageId, text, senderId, recipientId)
        val chat = chats.find { (it.senderId == senderId || it.senderId == recipientId) &&
                (it.recipientId == recipientId || it.recipientId == senderId) } ?: createChat(senderId, recipientId)
        chat.messages.add(message)
        chat.empty = false
        messageId++
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats.find { it.chatId == chatId } ?: throw NotFoundException("Чат не найден")
        if (chat.messages.isEmpty()) deleteChat(chatId)
        else {
            chat.messages.removeIf { it.messageId == messageId }
            if (chat.messages.isEmpty()) chat.empty = true
        }
    }
    fun createChat(senderId: Int, recipientId: Int): Chat {
        val chat = Chat(chatId, senderId, recipientId)
        chats.add(chat)
        chatId++
        return chats.last()
    }

    fun deleteChat(id: Int) {
        val chat = chats.find { it.chatId == id } ?: throw NotFoundException("Чат не найден")
        chat.messages.clear()
        chat.empty = true
    }
}