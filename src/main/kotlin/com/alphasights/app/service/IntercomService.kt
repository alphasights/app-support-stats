package com.alphasights.app.service

import com.alphasights.app.http.HttpClientFactory
import com.alphasights.app.model.intercom.Conversation
import com.alphasights.app.model.intercom.Tag
import com.alphasights.app.model.report.AppSupportReport
import com.alphasights.app.model.report.Application
import com.alphasights.app.model.report.Team
import com.alphasights.app.model.report.TypeOfConversation
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody


val INTERCOM_HTTP_CLIENT = HttpClientFactory().getIntercomOkHttpClient()
const val BASEURL = "https://api.intercom.io/"

class IntercomService {

//TODO: Implement this method. This is the method that the API entry point of this app will call
// This method controls the workflow to get users the final report

    fun methodWillBeCalledFromController() {
        getAllTags()
//        makeAnotherCall()
//        parseResponses()
//        downloadAsCSV()
    }

    //TODO: Implement real API call function, below is a simple example for how to use the http client to make call

    fun getAllTags(): List<Tag> {
        val request = Request.Builder()
            .url(BASEURL + "tags")
            .get() // get call, for post use .post()
            .build()
        val call = INTERCOM_HTTP_CLIENT.newCall(request = request)
        val response = call.execute()
        val allTags: MutableList<Tag> = mutableListOf()
        if (response.isSuccessful && response.body != null) {
            val stringTags = response.body!!.string()
            val jsonTags = Gson().fromJson(stringTags, JsonObject::class.java).getAsJsonArray("data")

            val teamTags: MutableList<Tag> = mutableListOf()
            val applicationTags: MutableList<Tag> = mutableListOf()
            val conversationTags: MutableList<Tag> = mutableListOf()

            jsonTags.forEach{
//                val tag = Gson().fromJson(it as JsonObject, Tag::class.java)
//                if (tag.name.first() == '!') {
//                    applicationTags.add(tag)
//                } else if (tag.name.first() == '_') {
//                    conversationTags.add(tag)
//                } else if (tag.name.first() == '$'){
//                    teamTags.add(tag)
//                }

                val tag = Gson().fromJson(it as JsonObject, Tag::class.java)
                allTags.add(tag)
            }
        }
        return allTags
    }

    fun searchConversations(
        tags: List<String>
    ): AppSupportReport {
        val allTags = getAllTags()
        val selectedTagIds = tags.map{selectedTag->
           val tag = allTags.firstOrNull() { selectedTag.equals(it.name, true) }
            if (tag !== null) {
                tag.id
            } else {
                null
            }
        }.filterNotNull()

        var selectedTagIdsQuery = "["
        selectedTagIds.forEachIndexed { index, tagName ->
            if (index !== 0) {
                selectedTagIdsQuery += ", "
            }
            selectedTagIdsQuery += """"$tagName""""
        }

        selectedTagIdsQuery += "]"

        val query = """{
            "query": {
                "operator": "AND",
                "value": [
                    {
                        "field": "tag_ids",
                        "operator": "IN",
                        "value": $selectedTagIdsQuery
                    },
                    {
                        "field": "created_at",
                        "operator": ">",
                        "value": 1633639743
                    }
                ]
            }
        }"""

        val body: RequestBody = RequestBody.create(contentType = "application/json; charset=utf-8".toMediaTypeOrNull(), query)

        val request = Request.Builder()
            .url(BASEURL + "conversations/search")
            .post(body)
            .build()

        val call = INTERCOM_HTTP_CLIENT.newCall(request = request)
        val response = call.execute()
        var appSupportReport = AppSupportReport(0, mutableListOf())

        if (response.isSuccessful && response.body != null) {
            val conversationsString = response.body!!.string()
            val jsonConversations = Gson().fromJson(conversationsString, JsonObject::class.java).getAsJsonArray("conversations")
            val groupedTeamConversations = HashMap<String, MutableList<Conversation>>()
            val groupedApplicationConversations = HashMap<String, MutableList<Conversation>>()

            jsonConversations.forEach {
                var teamTag: Tag? = null
                var applicationTag: Tag? = null

                val conversation = Conversation((it as JsonObject).getAsJsonPrimitive("id").asString, mutableListOf())

                it.asJsonObject.getAsJsonObject("tags").getAsJsonArray("tags").forEach {
                    val tag = Gson().fromJson(it as JsonObject, Tag::class.java)
                    (conversation.tags as MutableList).add(tag)
                    if (tag.name.first() == '$') {
                        teamTag = tag
                    } else if (tag.name.first() == '!') {
                        applicationTag = tag
                    }
                }

                if (teamTag != null) {
                    if (groupedTeamConversations[teamTag!!.name] != null) {
                        groupedTeamConversations[teamTag!!.name]?.add(conversation)
                    } else {
                        val conversations = mutableListOf(conversation)
                        groupedTeamConversations[teamTag!!.name] = conversations
                    }
                }
                if (applicationTag != null) {
                    if (groupedApplicationConversations[applicationTag!!.name] != null) {
                        groupedApplicationConversations[applicationTag!!.name]?.add(conversation)
                    } else {
                        val conversations = mutableListOf(conversation)
                        groupedApplicationConversations[applicationTag!!.name] = conversations
                    }
                }
            }

            val teamsHash = HashMap<String, Team>()
            groupedTeamConversations.forEach{
                var newTeam = Team(it.key, 0, mutableListOf())
                teamsHash[it.key] = newTeam

            }

            val applications = mutableListOf<Application>()
            groupedApplicationConversations.forEach{
                var (userEdCount, issueCount, adminCount, feedbackCount) = List(4) { 0 }
                var applicationTeam = ""

                it.value.forEach{
                    it.tags.forEach{
                        if (it.name.equals("_user-ed_")) {
                            userEdCount++
                        } else if (it.name.equals("_issue_")){
                            issueCount++
                        } else if (it.name.equals("_administrative_")){
                            adminCount++
                        } else if (it.name.equals("_feedback_")){
                            feedbackCount++
                        }

                        if (it.name.first() == '$') {
                            applicationTeam = it.name
                        }
                    }
                }
                val userEd = TypeOfConversation("User-Ed", userEdCount )
                val issue = TypeOfConversation("Issue", issueCount )
                val admin = TypeOfConversation("Admin", adminCount )
                val feedback = TypeOfConversation("Feedback", feedbackCount )

                val application = Application(it.key, it.value.size, listOf(userEd, issue, admin, feedback))
                applications.add(application)
                if (!applicationTeam.equals("")) {
                    (teamsHash[applicationTeam]?.applications as MutableList).add(application)
//                    teamsHash[applicationTeam]?.totalTicketsCount =
//                        teamsHash[applicationTeam]?.totalTicketsCount?.plus(it.value.size)!!
                }
            }
            teamsHash.forEach {
                it.value.totalTicketsCount = groupedTeamConversations[it.key]?.size ?: 0
            }

            var totalCount = 0
            val teams = teamsHash.map {
                totalCount += it.value.totalTicketsCount
                it.value
            }
            appSupportReport = AppSupportReport(totalCount, teams)
        }
        return appSupportReport
    }

//    fun LocalDateTime.date(): Date {
//        val zdt = this.atZone(ZoneId.systemDefault())
//        return Date.from(zdt.toInstant())
//    }
}
