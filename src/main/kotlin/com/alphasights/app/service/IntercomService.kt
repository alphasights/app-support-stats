package com.alphasights.app.service

import com.alphasights.app.http.HttpClientFactory
import com.alphasights.app.model.Conversation
import com.alphasights.app.model.Tag
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json


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

    fun getAllTags() {
        val request = Request.Builder()
            .url(BASEURL + "tags")
            .get() // get call, for post use .post()
            .build()
        val call = INTERCOM_HTTP_CLIENT.newCall(request = request)
        val response = call.execute()
        if (response.isSuccessful && response.body != null) {
            val stringTags = response.body!!.string()
            val jsonTags = Gson().fromJson(stringTags, JsonObject::class.java).getAsJsonArray("data")

            val teamTags: MutableList<Tag> = mutableListOf()
            val applicationTags: MutableList<Tag> = mutableListOf()
            val conversationTags: MutableList<Tag> = mutableListOf()

            jsonTags.forEach{
                val tag = Gson().fromJson(it as JsonObject, Tag::class.java)
                if (tag.name.first() == '!') {
                    applicationTags.add(tag)
                } else if (tag.name.first() == '_') {
                    conversationTags.add(tag)
                } else if (tag.name.first() == '$'){
                    teamTags.add(tag)
                }
            }

            val x = 1
        }
    }

    fun searchConversations(
//        tagSets: List<List<Tag>>
    ) {
        val query = """{
            "query": {
                "operator": "AND",
                "value": [
                    {
                    "field": "updated_at",
                    "operator": ">",
                    "value": 1633123999
                    },
                    {
                    "field": "created_at",
                    "operator": ">",
                    "value": 1633123999
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
        if (response.isSuccessful && response.body != null) {
            val conversationsString = response.body!!.string()
            val jsonConversations = Gson().fromJson(conversationsString, JsonObject::class.java).getAsJsonArray("conversations")

            val arrConvo = jsonConversations.map{
                Gson().fromJson(it as JsonObject, Conversation::class.java)
            }
        }
    }
}
