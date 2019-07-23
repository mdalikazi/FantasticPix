package com.alikazi.codetest.weatherzone.models

import androidx.lifecycle.MutableLiveData

object RequestResponseModels {

    class QueryRequest(query: String) {
        var query = query
    }

    class QueryResponse {
        val _photos = MutableLiveData<ArrayList<Photo>?>()
        val _networkErrors = MutableLiveData<String>()
    }

}