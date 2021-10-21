package com.alphasights.app.model

data class Statistics(
    val count_assignments: Int,
    val count_conversation_parts: Int,
    val count_reopens: Int,
    val first_admin_reply_at: Long,
    val first_assignment_at: Long,
    val first_close_at: Long,
    val first_contact_reply_at: Long,
    val last_admin_reply_at: Long,
    val last_assignment_admin_reply_at: Long,
    val last_assignment_at: Long,
    val last_close_at: Long,
    val last_closed_by: LastClosedBy,
    val last_contact_reply_at: Long,
    val median_time_to_reply: Int,
    val time_to_admin_reply: Int,
    val time_to_assignment: Int,
    val time_to_first_close: Int,
    val time_to_last_close: Int
)
