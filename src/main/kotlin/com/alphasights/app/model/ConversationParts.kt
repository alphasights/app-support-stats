package com.alphasights.app.model

data class ConversationParts(
    val conversation_parts: List<ConversationPart>,
    val total_count: Int,
    val type: String
)
