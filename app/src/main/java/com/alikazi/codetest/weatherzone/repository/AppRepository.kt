package com.alikazi.codetest.weatherzone.repository

import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.DLog

class AppRepository {

    fun getPhotoWithQueryFromApi(request: RequestResponseModels.ViewModelQueryRequest):
            RequestResponseModels.ViewModelQueryResponse {
	    DLog.i("getPhotoWithQueryFromApi")
        var viewModelQueryResponse = RequestResponseModels.ViewModelQueryResponse()
        val url = NetworkHelper.queryUrlBuilder(request.query)
        DLog.d("url $url")
        val okHttpResponse = NetworkHelper.requestEndpoint(url, "photo")
        if (okHttpResponse != null && okHttpResponse?.isSuccessful) {
            val queryResponse = NetworkHelper.parseQueryResponseFromJson(okHttpResponse.body?.string())
            viewModelQueryResponse._photos.postValue(queryResponse?.photos as ArrayList<Photo>?)
        } else {
            viewModelQueryResponse._networkErrors.postValue(okHttpResponse?.body?.string())
            DLog.d("network errors: ${viewModelQueryResponse._networkErrors}")
        }

        return viewModelQueryResponse
    }


}