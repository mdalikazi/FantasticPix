package com.alikazi.codetest.weatherzone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alikazi.codetest.weatherzone.main.AppRepository
import com.alikazi.codetest.weatherzone.models.RequestResponseModels

class PhotoViewModel(private val repository: AppRepository) : ViewModel() {

    private var queryRequestLiveData = MutableLiveData<RequestResponseModels.QueryRequest>()
    private var queryResponseLiveData = Transformations.map(queryRequestLiveData) {
        repository.getPhotoWithQueryFromApi(it)
    }

    fun getPhotosWithQuery(request: RequestResponseModels.QueryRequest) {

    }

}