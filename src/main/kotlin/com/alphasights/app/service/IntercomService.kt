package com.alphasights.app.service

import com.alphasights.app.http.HttpClientFactory
import okhttp3.Request

val INTERCOM_HTTP_CLIENT = HttpClientFactory().getIntercomOkHttpClient()

class IntercomService {

//TODO: Implement this method. This is the method that the API entry point of this app will call
// This method controls the workflow to get users the final report

//    fun methodWillBeCalledFromController(some parameters) {
//        makeCall()
//        makeAnotherCall()
//        parseResponses()
//        downloadAsCSV()
//    }

    //TODO: Implement real API call function, below is a simple example for how to use the http client to make call

//    fun makecall(some parameters) {
//        val request = Request.Builder()
//            .url("url")
//            .get() // get call, for post use .post()
//            .build()
//        val call = INTERCOM_HTTP_CLIENT.newCall(request = request)
//        val response = call.execute()
//        if(response.isSuccessful && response.body != null){
//            // do something
//        }
//        else{
//            // do something
//        }
//    }
}
