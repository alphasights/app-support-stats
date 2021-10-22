package com.alphasights.app.model

data class Team(
    val name: String,
    val totalTicketsCount: Int,
    val applications: List<Application>
)
