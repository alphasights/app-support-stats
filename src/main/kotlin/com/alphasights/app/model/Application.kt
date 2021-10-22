package com.alphasights.app.model

data class Application(
    val name: String,
    val totalTicketsCount: Int,
    val typeOfConversations: List<TypeOfConversation>
)
