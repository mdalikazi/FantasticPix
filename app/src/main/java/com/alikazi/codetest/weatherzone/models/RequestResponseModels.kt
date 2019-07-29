package com.alikazi.codetest.weatherzone.models

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

object RequestResponseModels {

    class ViewModelQueryRequest(query: String, page: Int) {
        var query = query
        var page = page
    }

    class ViewModelQueryResponse {
        val _photos = MutableLiveData<PagedList<Photo>?>()
        val _networkErrors = MutableLiveData<String>()
    }

}