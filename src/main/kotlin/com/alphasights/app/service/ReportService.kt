package com.alphasights.app.service

import com.alphasights.app.model.report.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.apache.commons.lang3.StringUtils.lowerCase
import org.springframework.stereotype.Service
import java.io.File

@Service
class ReportService {
    private val outputFileName = "report.csv"
    private val CSV_HEADER = listOf("Tag", "Count", "User-Ed", "Issue", "Admin", "Feedback", "Total")

    fun generateReport(appSupportReport: AppSupportReport) : File {
        val home = System.getProperty("user.home")
        val path = home + File.separator.toString() + "Downloads" + File.separator.toString() + outputFileName
        val file = File(path)
        file.createNewFile();

        csvWriter().open(path) {
            writeRow(CSV_HEADER)

            for (team in appSupportReport.teams) {
                writeRow(
                        team.name.toUpperCase(),
                        team.totalTicketsCount
                )

                for (application in team.applications) {
                    val typeCounts = getTypeCounts(application.typeOfConversations)
                    writeRow(
                            application.name,
                            application.totalTicketsCount,
                            typeCounts.userEdCount,
                            typeCounts.issueCount,
                            typeCounts.adminCount,
                            typeCounts.feedbackCount,
                            application.totalTicketsCount
                    )
                }
            }

            writeRow("")
            writeRow("Primary Tag Breakdown")
            writeRow("Tag", "Count")

            for (team in appSupportReport.teams) {
                writeRow(team.name.toUpperCase(), team.totalTicketsCount)
            }

            writeRow("")
            writeRow("")
            writeRow("Total Count", appSupportReport.totalTicketsCount)
        }
        return file
    }

    private fun getTypeCounts(typeOfConversations: List<TypeOfConversation>) : TypeCounts {
        var (userEdCount, issueCount, adminCount, feedbackCount) = List(4) { 0 }

        for (typeOfConversation in typeOfConversations) {
            when (lowerCase(typeOfConversation.name)) {
                "user_ed" -> {
                    userEdCount += typeOfConversation.count
                }
                "issue" -> {
                    issueCount += typeOfConversation.count
                }
                "admin" -> {
                    adminCount += typeOfConversation.count
                }
                "feedback" -> {
                    feedbackCount += typeOfConversation.count
                }
            }
        }
        return TypeCounts(userEdCount, issueCount, adminCount, feedbackCount)
    }
}
