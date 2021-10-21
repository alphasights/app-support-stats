package com.alphasights.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.alphasights.app.service.IntercomService

@SpringBootApplication
class AppSupportStatsApplication(
) {
    val intercomService = IntercomService()
    fun makeCall() {
//        intercomService.getAllTags()
        intercomService.searchConversations()

    }
}

fun main(args: Array<String>) {
    runApplication<AppSupportStatsApplication>(*args)
    val appSupportStatsApplication = AppSupportStatsApplication()
    appSupportStatsApplication.makeCall()
}
