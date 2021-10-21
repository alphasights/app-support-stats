package com.alphasights.app.model

data class ConversationPart(
    val assigned_to: String,
    val attachments: List<Any>,
    val author: Author,
    val body: String,
    val created_at: Long,
    val external_id: Int,
    val id: String,
    val notified_at: Long,
    val part_type: String,
    val redacted: Boolean,
    val type: String,
    val updated_at: Long
)
