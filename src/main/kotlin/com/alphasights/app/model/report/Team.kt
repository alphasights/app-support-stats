package com.alphasights.app.model.report

data class Team(
    val name: String,
    val totalTicketsCount: Int,
    val applications: List<Application>
)
