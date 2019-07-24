package com.alikazi.codetest.weatherzone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.repository.AppRepository
import com.alikazi.codetest.weatherzone.utils.DLog

class PhotoViewModel(private val repository: AppRepository) : ViewModel() {

    private var queryRequestLiveData = MutableLiveData<RequestResponseModels.ViewModelQueryRequest>()
    private var queryResponseLiveData = Transformations.map(queryRequestLiveData) {
        repository.getPhotoWithQueryFromApi(it)
    }

    var photos = Transformations.switchMap(queryResponseLiveData) {
        it._photos
    }

    var networkErrors = Transformations.switchMap(queryResponseLiveData) {
        it._networkErrors
    }

    fun getPhotosWithQuery(request: RequestResponseModels.ViewModelQueryRequest) {
	    DLog.i("getPhotosWithQuery")
        queryRequestLiveData.postValue(request)
    }

}