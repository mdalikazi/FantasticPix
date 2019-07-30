package com.alikazi.codetest.weatherzone.repository

import android.net.Uri
import com.alikazi.codetest.weatherzone.models.QueryResponse
import com.alikazi.codetest.weatherzone.utils.Constants
import com.alikazi.codetest.weatherzone.utils.DLog
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

object NetworkHelper {

    fun get(url: URL, callback: Callback) {
        val request = Request.Builder()
            .header(Constants.HEADER_KEY_AUTHORIZATION, Constants.PEXELS_API_KEY)
            .url(url)
            .build()

        OkHttpClient().newCall(request).enqueue(callback)
    }

    fun queryUrlBuilder(query: String, page: Int): URL {
        val builder = Uri.Builder()
            .scheme(Constants.SCHEME_HTTPS)
            .authority(Constants.AUTHORITY)
            .appendPath(Constants.PATH_V1)
            .appendPath(Constants.PATH_SEARCH)
            .appendQueryParameter(Constants.PARAM_QUERY, query)
            .appendQueryParameter(Constants.PARAM_PER_PAGE, Constants.PARAM_VALUE_PER_PAGE)
            .appendQueryParameter(Constants.PARAM_PAGE, page.toString())
        return URL(builder.build().toString())
    }

    fun parseQueryResponseFromJson(jsonString: String?): QueryResponse {
        try {
            return Gson().fromJson(jsonString, QueryResponse::class.java)
        } catch (jsonSyntaxException: JsonSyntaxException) {
            DLog.d("jsonSyntaxException $jsonSyntaxException")
        } catch (illegalStateException: IllegalStateException) {
            DLog.d("illegalStateException $illegalStateException")
        }
        return QueryResponse()
    }
}