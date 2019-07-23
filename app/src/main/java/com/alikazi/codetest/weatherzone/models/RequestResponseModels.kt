package com.alikazi.codetest.weatherzone.models

import androidx.lifecycle.MutableLiveData

object RequestResponseModels {

    class QueryRequest(private val query: String)

    class QueryResponse {

        private val _response = MutableLiveData<String>()
        private val _networkErrors = MutableLiveData<String>()

    }

}