package com.alphasights.app.model.report

data class Team(
    val name: String,
    var totalTicketsCount: Int,
    val applications: List<Application>
)
