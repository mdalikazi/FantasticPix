package com.alikazi.codetest.weatherzone.repository

import android.util.Log
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.DLog
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class AppRepository {

    fun loadPhotos() {
        val dataSourceFactory = cache.getAllPhotos()
        val boundaryCallback = PhotosBoundaryCallback(requestQueueHelper, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, 5)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return PhotosPagedList(data, networkErrors)
    }

    fun getPhotoWithQueryFromApi(request: RequestResponseModels.ViewModelQueryRequest):
            RequestResponseModels.ViewModelQueryResponse {
        DLog.i("getPhotoWithQueryFromApi")
        var viewModelQueryResponse = RequestResponseModels.ViewModelQueryResponse()
        val url = NetworkHelper.queryUrlBuilder(request.query, request.page)

        NetworkHelper.get(url, object: okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                viewModelQueryResponse._networkErrors.postValue(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val queryResponse = NetworkHelper.parseQueryResponseFromJson(response.body?.string())
                viewModelQueryResponse._photos.postValue(queryResponse?.photos as PagedList<Photo>?)
            }
        })

        return viewModelQueryResponse
    }

}