package com.alphasights.app.controller

import com.alphasights.app.service.IntercomService
import com.alphasights.app.service.ReportService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.IOException


@RestController
@RequestMapping("report")
class ReportsApi {
    @GetMapping("/intercom")
    @Throws(IOException::class)
    fun intercomReport(
        @RequestParam(name = "tag_names", required = true) tagNames: String,
        @RequestParam(name = "date_range", required = false) dateRange: String?
    ) : ResponseEntity<ByteArrayResource> {
        try {
            // TODO:
            //  1.Really use tag_names and date_range - nice to have, when UI is ready
            //  for now hard coded to everything in two weeks
            //  2.replace the lines that creates test file with real method call to get real report .csv file - must do
            val tags = listOf("""${"$"}comms""","""${"$"}Knowledge Capture""")
            val appSupportReport = IntercomService().searchConversations(tags)
            val file = ReportService().generateReport(appSupportReport)

            val resource = ByteArrayResource(file.readBytes())

            val headers = HttpHeaders()
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.txt")
            headers.add(HttpHeaders.CACHE_CONTROL, "no-store")
            headers.add(HttpHeaders.EXPIRES, "0")

            return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource)
        } catch (e: Exception) {
            e.printStackTrace()
            val resource = e.message?.let { ByteArrayResource(it.toByteArray()) }
            return ResponseEntity.ok().body(resource)
        }
    }
}
