package com.alphasights.app.model.intercom

data class Source(
    val attachments: List<Any>,
    val author: Author,
    val body: String,
    val delivered_as: String,
    val id: String,
    val redacted: Boolean,
    val subject: String,
    val type: String,
    val url: String
)
