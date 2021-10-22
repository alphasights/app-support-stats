package com.alphasights.app.model.report

data class Application(
    val name: String,
    val totalTicketsCount: Int,
    val typeOfConversations: List<TypeOfConversation>
)
