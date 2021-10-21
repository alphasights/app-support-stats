package com.alphasights.app.model

data class Conversation(
    val adminAssigneeId: Int,
    val contacts: List<Contact>,
    val conversationParts: ConversationParts,
    val conversationRating: ConversationRating,
    val createdAt: Long,
    val customAttributes: CustomAttributes,
    val firstContactReply: FirstContactReply,
    val id: String,
    val open: Boolean,
    val priority: String,
    val read: Boolean,
    val slaApplied: SlaApplied,
    val snoozedUntil: Long,
    val source: Source,
    val state: String,
    val statistics: Statistics,
    val tags: Tags,
    val teamAssigneeId: Int,
    val teammates: List<Teammate>,
    val title: String,
    val type: String,
    val updatedAt: Long,
    val waitingSince: Long
)
