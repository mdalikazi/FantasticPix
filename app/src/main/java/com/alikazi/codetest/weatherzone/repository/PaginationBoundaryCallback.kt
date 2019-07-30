package com.alikazi.codetest.weatherzone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.alikazi.codetest.weatherzone.database.AppDatabase
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.DLog
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class PaginationBoundaryCallback(private val request: RequestResponseModels.ViewModelQueryRequest,
                                 private val databaseInstance: AppDatabase) : PagedList.BoundaryCallback<Photo>() {

	private var lastRequestedPage = 1
	private var isRequestInProgress = false
	private var _networkErrors = MutableLiveData<String>()
	val networkErrors: LiveData<String> get() = _networkErrors

	override fun onZeroItemsLoaded() {
		DLog.i("onZeroItemsLoaded")
		if (!isRequestInProgress) {
			requestAndSaveData()
		}
	}

	override fun onItemAtEndLoaded(itemAtEnd: Photo) {
		DLog.i("onItemAtEndLoaded")
		if (!isRequestInProgress) {
			requestAndSaveData()
		}
	}

	private fun requestAndSaveData() {
		isRequestInProgress = true
		DLog.i("getPhotoWithQueryFromApi")
		val url = NetworkHelper.queryUrlBuilder(request.query, lastRequestedPage)
		DLog.d("url $url")
		NetworkHelper.get(url, object: Callback {
			override fun onFailure(call: Call, e: IOException) {
				onFailure(e.message)
			}

			override fun onResponse(call: Call, response: Response) {
				val queryResponse = NetworkHelper.parseQueryResponseFromJson(response.body?.string())
				if (queryResponse != null) {
					databaseInstance.photoDao().savePhotos(queryResponse.photos)
					lastRequestedPage++
					isRequestInProgress = false
				} else {
					onFailure("Error")
				}
			}
		})
	}

	private fun onFailure(errorMessage: String?) {
		_networkErrors.postValue(errorMessage)
		isRequestInProgress = false
	}

	/*fun getPhotoWithQueryFromApi(request: RequestResponseModels.ViewModelQueryRequest):
			RequestResponseModels.PhotosPagedListResponse {
		DLog.i("getPhotoWithQueryFromApi")
		var viewModelQueryResponse = RequestResponseModels.PhotosPagedListResponse()
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
	}*/
}