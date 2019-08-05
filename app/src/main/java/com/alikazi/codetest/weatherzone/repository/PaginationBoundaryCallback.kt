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
import java.util.concurrent.Executors
class PaginationBoundaryCallback(private val request: RequestResponseModels.ViewModelQueryRequest,
                                 private val databaseInstance: AppDatabase) : PagedList.BoundaryCallback<Photo>() {

	private var executor = Executors.newSingleThreadExecutor()
	private var lastRequestedPage = 1
	private var isRequestInProgress = false
	private var _networkErrors = MutableLiveData<String>()
	val networkErrors: LiveData<String> get() = _networkErrors

	override fun onZeroItemsLoaded() {
		DLog.i("onZeroItemsLoaded")
		executor.execute {
			if (!isRequestInProgress && checkIfLastQueryMatches()) {
				requestAndSaveData()
			}
		}
	}

	override fun onItemAtEndLoaded(itemAtEnd: Photo) {
		DLog.i("onItemAtEndLoaded")
		if (!isRequestInProgress) {
			requestAndSaveData()
		}
	}

	private fun requestAndSaveData() {
		DLog.i("requestAndSaveData")
		isRequestInProgress = true
		val url = NetworkHelper.queryUrlBuilder(request.query, lastRequestedPage)
		DLog.d("url $url")

		NetworkHelper.get(url, object : Callback {
			override fun onFailure(call: Call, e: IOException) {
				onFailure(e.message)
			}

			override fun onResponse(call: Call, response: Response) {
				val queryResponse = NetworkHelper.parseQueryResponseFromJson(response.body?.string())
				executor.execute {
					databaseInstance.photoDao().savePhotos(queryResponse.photos)
				}
				lastRequestedPage++
				isRequestInProgress = false
			}
		})
	}

	private fun onFailure(errorMessage: String?) {
		_networkErrors.postValue(errorMessage)
		isRequestInProgress = false
	}

	private fun checkIfLastQueryMatches(): Boolean =
		when (databaseInstance.photoDao().getLastQuery() == request.query) {
			false -> {
				databaseInstance.photoDao().deleteAll()
				databaseInstance.photoDao().saveQuery(request.query)
				true
			}
			else -> false
		}

}

