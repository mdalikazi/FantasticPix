package com.alikazi.codetest.weatherzone.repository

import android.util.Log
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.Constants

class AppRepository {


    fun getPhotoWithQueryFromApi(request: RequestResponseModels.QueryRequest): RequestResponseModels.QueryResponse {
        var queryResponse = RequestResponseModels.QueryResponse()
        val url = NetworkHelper.queryUrlBuilder(request.query)
        val okHttpResponse = NetworkHelper.requestEndpoint(url, "photo")
        if (okHttpResponse != null && okHttpResponse?.isSuccessful) {
            val photos = NetworkHelper.parsePhotosFromJson(okHttpResponse.body.toString())
            queryResponse._photos.postValue(photos as ArrayList<Photo>)
        } else {
            queryResponse._networkErrors.postValue(okHttpResponse?.body?.string())
        }

        Log.d(Constants.LOG_TAG, "$queryResponse")

        return queryResponse
    }


}