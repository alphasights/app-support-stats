package com.alphasights.app.model.report

data class AppSupportReport(
    val totalTicketsCount: Int,
    val teams: List<Team>
)
