package com.alikazi.codetest.weatherzone.repository

import android.net.Uri
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.utils.Constants
import com.google.gson.Gson
import okhttp3.Response
import java.net.URL
import java.util.concurrent.Executors

object NetworkHelper {

    fun requestEndpoint(url: String, tag: String): Response? {
        var okHttpResponse: Response? = null
        var requests = ArrayList<CallableRequestsHelper>()
        requests.add(CallableRequestsHelper(url, tag))
        val executor = Executors.newSingleThreadExecutor()
        val results = executor.invokeAll(requests)
        for (response in results) {
            okHttpResponse = response.get()
        }
        return okHttpResponse
    }

    fun queryUrlBuilder(query: String): String {
        val builder = Uri.Builder().path(Constants.URL_SEARCH)
            .appendQueryParameter(Constants.PARAM_QUERY, query)
            .appendQueryParameter(Constants.PARAM_PER_PAGE, Constants.PARAM_VALUE_PER_PAGE)
            .appendQueryParameter(Constants.PARAM_PAGE, Constants.PARAM_VALUE_PAGE)
        return URL(builder.build().toString()).toString()
    }

    fun parsePhotosFromJson(jsonString: String?): List<Photo> {
        return Gson().fromJson(jsonString, Array<Photo>::class.java).toList()
    }
}