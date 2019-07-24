package com.alikazi.codetest.weatherzone.repository

import android.net.Uri
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.utils.Constants
import com.alikazi.codetest.weatherzone.utils.DLog
import com.google.gson.Gson
import okhttp3.Response
import java.net.URL
import java.util.concurrent.Executors

object NetworkHelper {

    fun requestEndpoint(url: URL, tag: String): Response? {
	    DLog.i("requestEndpoint")
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

    fun parsePhotosFromJson(jsonString: String?): List<Photo> {
        return Gson().fromJson(jsonString, Array<Photo>::class.java).toList()
    }
}