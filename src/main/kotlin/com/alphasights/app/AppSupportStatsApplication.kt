package com.alphasights.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.alphasights.app.service.IntercomService
import com.alphasights.app.service.ReportService

@SpringBootApplication
class AppSupportStatsApplication(
) {
    val intercomService = IntercomService()
    fun makeCall() {
        val tags = listOf("""${"$"}comms""","""${"$"}Knowledge Capture""")
        val appSupportReport = intercomService.searchConversations(tags)
        ReportService().generateReport(appSupportReport)
    }
}

fun main(args: Array<String>) {
//    runApplication<AppSupportStatsApplication>(*args)
    val appSupportStatsApplication = AppSupportStatsApplication()
    appSupportStatsApplication.makeCall()
}
