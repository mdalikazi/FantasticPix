package com.alikazi.codetest.weatherzone.repository

import android.net.Uri
import com.alikazi.codetest.weatherzone.models.QueryResponse
import com.alikazi.codetest.weatherzone.utils.Constants
import com.alikazi.codetest.weatherzone.utils.DLog
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.Response
import java.net.URL
import java.util.concurrent.Executors

object NetworkHelper {

    val thread = Executors.newSingleThreadExecutor()

    fun requestEndpoint(url: URL, tag: String): Response? {
	    DLog.i("requestEndpoint")
        var okHttpResponse: Response? = null
        var requests = ArrayList<CallableRequestsHelper>()
        requests.add(CallableRequestsHelper(url, tag))
        val results = thread.invokeAll(requests)
        for (response in results) {
            okHttpResponse = response.get()
        }
        return okHttpResponse
    }

    fun queryUrlBuilder(query: String): URL {
        val builder = Uri.Builder()
            .scheme(Constants.SCHEME_HTTPS)
            .authority(Constants.AUTHORITY)
            .appendPath(Constants.PATH_V1)
            .appendPath(Constants.PATh_SEARCH)
            .appendQueryParameter(Constants.PARAM_QUERY, query)
            .appendQueryParameter(Constants.PARAM_PER_PAGE, Constants.PARAM_VALUE_PER_PAGE)
            .appendQueryParameter(Constants.PARAM_PAGE, Constants.PARAM_VALUE_PAGE)
        return URL(builder.build().toString())
    }

    fun parseQueryResponseFromJson(jsonString: String?): QueryResponse? {
        try {
            return Gson().fromJson(jsonString, QueryResponse::class.java)
        } catch (jsonSyntaxException: JsonSyntaxException) {
            DLog.d("jsonSyntaxException $jsonSyntaxException")
        } catch (illegalStateException: IllegalStateException) {
            DLog.d("illegalStateException $illegalStateException")
        }
        DLog.d("jsonString $jsonString")
        return null
    }
}