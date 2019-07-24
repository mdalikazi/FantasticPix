package com.alikazi.codetest.weatherzone.repository

import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.DLog

class AppRepository {


    fun getPhotoWithQueryFromApi(request: RequestResponseModels.QueryRequest): RequestResponseModels.QueryResponse {
	    DLog.i("getPhotoWithQueryFromApi")
        var queryResponse = RequestResponseModels.QueryResponse()
        val url = NetworkHelper.queryUrlBuilder(request.query)
	    DLog.d("url $url")
        val okHttpResponse = NetworkHelper.requestEndpoint(url, "photo")
        if (okHttpResponse != null && okHttpResponse?.isSuccessful) {
            val photos = NetworkHelper.parsePhotosFromJson(okHttpResponse.body.toString())
            queryResponse._photos.postValue(photos as ArrayList<Photo>)
        } else {
            queryResponse._networkErrors.postValue(okHttpResponse?.body?.string())
        }
        DLog.d("network errors: ${queryResponse._networkErrors}")
	    DLog.d("photos: ${queryResponse._photos}")

        return queryResponse
    }


}