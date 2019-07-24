package com.alikazi.codetest.weatherzone.models

import androidx.lifecycle.MutableLiveData

object RequestResponseModels {

    class ViewModelQueryRequest(query: String) {
        var query = query
    }

    class ViewModelQueryResponse {
        val _photos = MutableLiveData<ArrayList<Photo>?>()
        val _networkErrors = MutableLiveData<String>()
    }

}