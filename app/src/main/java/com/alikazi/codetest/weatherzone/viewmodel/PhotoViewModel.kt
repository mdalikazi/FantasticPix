package com.alikazi.codetest.weatherzone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.repository.AppRepository

class PhotoViewModel(private val repository: AppRepository) : ViewModel() {

    private var queryRequestLiveData = MutableLiveData<RequestResponseModels.ViewModelQueryRequest>()
    private var queryResponseLiveData = Transformations.map(queryRequestLiveData) {
        repository.getPhotoWithQueryFromApi(it)
    }

    var photos: LiveData<ArrayList<Photo>?> = Transformations.switchMap(queryResponseLiveData) {
        it._photos
    }

    var networkErrors: LiveData<String> = Transformations.switchMap(queryResponseLiveData) {
        it._networkErrors
    }

    fun getPhotosWithQuery(request: RequestResponseModels.ViewModelQueryRequest) {
        queryRequestLiveData.postValue(request)
    }

}