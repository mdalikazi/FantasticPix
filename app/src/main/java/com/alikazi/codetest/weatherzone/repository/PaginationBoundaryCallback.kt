package com.alikazi.codetest.weatherzone.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.utils.DLog

class PaginationBoundaryCallback : PagedList.BoundaryCallback<Photo>() {

	private var lastRequestedPage = 1
	private var isRequestInProgress = false
	private val _networkErrors = MutableLiveData<String>()

	override fun onZeroItemsLoaded() {
		DLog.i("onZeroItemsLoaded")
		requestAndSaveData()
	}

	override fun onItemAtEndLoaded(itemAtEnd: Photo) {
		DLog.i("onItemAtEndLoaded")
		requestAndSaveData()
	}

	private fun requestAndSaveData() {
		if (isRequestInProgress) {
			return
		}

		isRequestInProgress = true
	}
}