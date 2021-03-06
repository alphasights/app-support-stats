package com.alphasights.app.model.intercom

data class ConversationRating(
    val contact: Contact,
    val created_at: Long,
    val rating: Int,
    val remark: String,
    val teammate: Teammate
)
