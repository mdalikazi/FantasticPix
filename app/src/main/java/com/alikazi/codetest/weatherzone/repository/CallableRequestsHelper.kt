package com.alikazi.codetest.weatherzone.repository

import com.alikazi.codetest.weatherzone.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.Callable

class CallableRequestsHelper (private val url: String,
                              private val tag: String) : Callable<Response> {

    override fun call(): Response {
        val request = Request.Builder()
            .header(Constants.HEADER_KEY_AUTHORIZATION, Constants.PEXELS_API_KEY)
            .url(url)
            .tag(tag)
            .build()

        return return OkHttpClient().newCall(request).execute()
    }
}