package com.alikazi.codetest.weatherzone.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

object RequestResponseModels {

    data class ViewModelQueryRequest(val query: String)

    data class PhotosPagedListResponse(val _photos: LiveData<PagedList<Photo>> = MutableLiveData(),
                                       val _networkErrors: LiveData<String> = MutableLiveData())

}