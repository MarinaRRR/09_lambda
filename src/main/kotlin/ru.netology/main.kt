package ru.netology

import ru.netology.service.ChatService

fun main() {

    ChatService.sendMessage(1,2, "111111")
    ChatService.sendMessage(1,2, "22222222")
    ChatService.sendMessage(1,3, "33333333")
    ChatService.sendMessage(1,3, "444444444")
    ChatService.getChats(2)
    //ChatService.deleteChat(3)


}