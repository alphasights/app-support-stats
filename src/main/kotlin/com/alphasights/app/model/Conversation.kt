package com.alphasights.app.model

data class Conversation(
    val admin_assignee_id: Int,
    val contacts: List<Contact>,
    val conversation_parts: ConversationParts,
    val conversation_rating: ConversationRating,
    val created_at: Long,
    val custom_attributes: CustomAttributes,
    val first_contact_reply: FirstContactReply,
    val id: String,
    val open: Boolean,
    val priority: String,
    val read: Boolean,
    val sla_applied: SlaApplied,
    val snoozed_until: Long,
    val source: Source,
    val state: String,
    val statistics: Statistics,
    val tags: Tags,
    val team_assignee_id: Int,
    val teammates: List<Teammate>,
    val title: String,
    val type: String,
    val updated_at: Long,
    val waiting_since: Long
)
