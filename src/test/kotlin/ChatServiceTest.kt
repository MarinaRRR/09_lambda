package ru.netology
import org.junit.Test

import org.junit.Assert.*
import ru.netology.data.Chat
import ru.netology.data.Message
import ru.netology.exeptions.NotFoundException
import ru.netology.service.ChatService

class ChatServiceTest {

    @Test
    fun createChat() {
        val service = ChatService
        service.createChat(1, 10)
        val expected = Chat(1, 1, 10)
        val result = service.createChat(1, 10)

        assertEquals(expected, result)
    }

    @Test
    fun getChats() {
        val service = ChatService
        val chat1 = Chat(0, 1, 2)
        val chat2 = Chat(1, 2, 4)
        val expected = mutableListOf<Chat>()
        expected.add(chat1)
        expected.add(chat2)
        println(service.createChat(1, 2))
        println(service.createChat(2, 4))
        val result = service.getChats(2)

        assertEquals(expected, result)
    }

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService
        service.sendMessage(1, 10, "Сообщение1")
        service.sendMessage(1, 5, "Сообщение2")
        service.sendMessage(2, 4, "Сообщение3")
        val result = service.getUnreadChatsCount(1)

        assertEquals(1, result)
    }

    @Test
    fun deleteChat() {
        val service = ChatService
        val expected = Chat(0, 1, 10, empty = true)
        val result = service.createChat(1, 10)
        service.sendMessage(1, 10, "Сообщение1")
        service.deleteChat(0)

        assertEquals(expected, result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteChatFalse() {
        val service = ChatService
        service.createChat(1, 10)
        service.deleteChat(4)
    }

    @Test
    fun sendMessage() {
        val service = ChatService
        val message1 = Message(0, "Сообщение1", 1, 2, read = true)
        val message2 = Message(1, "Сообщение2", 2, 1, read = true)
        val expected = mutableListOf<Message>()
        expected.add(message1)
        expected.add(message2)
        service.sendMessage(message1.senderId, message1.recipientId, message1.text)
        service.sendMessage(message2.senderId, message2.recipientId, message2.text)
        val result = service.getMessage(1, 2, 2)

        assertEquals(expected, result)
    }

    @Test
    fun getMessage() {
        val service = ChatService
        val message1 = Message(0, "Сообщение1", 1, 10, read = true)
        val message2 = Message(1, "Сообщение2", 10, 1, read = true)
        val expected = mutableListOf<Message>()
        expected.add(message1)
        expected.add(message2)
        service.sendMessage(message1.senderId, message1.recipientId, message1.text)
        service.sendMessage(message2.senderId, message2.recipientId, message2.text)
        val result = service.getMessage(1, 0, 2)
        println(expected)
        println(result)


        assertEquals(expected, result)
    }

    @Test(expected = NotFoundException::class)
    fun getMessageFalse() {
        val service = ChatService
        service.sendMessage(1, 10, "Сообщение1")
        service.sendMessage(10, 1, "Сообщение2")
        service.getMessage(2, 0, 1)

    }

    @Test
    fun getLastMessage() {
        val service = ChatService
        val message1 = Message(0, "Сообщение1", 1, 2, read = false)
        val message2 = Message(1, "Сообщение2", 10, 1, read = false)
        val expected = mutableListOf("Нет сообщений", "Сообщение1", "Сообщение2")
        service.createChat(1, 3)
        service.sendMessage(message1.senderId, message1.recipientId, message1.text)
        service.sendMessage(message2.senderId, message2.recipientId, message2.text)
        val result = service.getLastMessage(1)

        assertEquals(expected, result)
    }

    @Test
    fun deleteMessage() {
        val service = ChatService
        val expected = Chat(0, 1, 2, empty = true)
        service.sendMessage(1, 2, "Сообщение1")
        service.deleteMessage(0)
        val chatService = service.getChats(1)
        val result = chatService.last()

        assertEquals(expected, result)
    }
}