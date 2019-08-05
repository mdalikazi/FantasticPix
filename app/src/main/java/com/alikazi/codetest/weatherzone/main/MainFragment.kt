package com.alikazi.codetest.weatherzone.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.alikazi.codetest.weatherzone.R
import com.alikazi.codetest.weatherzone.database.AppDatabase
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.*
import com.alikazi.codetest.weatherzone.utils.Helpers.openUrlInBrowser
import com.alikazi.codetest.weatherzone.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), WZSearchView.SearchViewEventsListener {

	private lateinit var photoViewModel: PhotoViewModel
	private lateinit var photosAdapter: PhotosAdapter
	private lateinit var activityContext: Context
	private lateinit var adapterObserver: RecyclerView.AdapterDataObserver

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		retainInstance = true

		WZSearchView.setSearchViewEventsListener(this)
		activityContext = activity!!.applicationContext
		photosAdapter = PhotosAdapter(activityContext)
		photoViewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory(AppDatabase.getInstance(activityContext)))
			.get(PhotoViewModel::class.java)

		photoViewModel.photos.observe(this, Observer {
			DLog.d("photos size ${it?.size}")
			photosAdapter.submitList(it)
			showHideEmptyMessageWithError(it.isNullOrEmpty())
		})

		photoViewModel.networkErrors.observe(this, Observer {
			DLog.d("error $it")
			showHideEmptyMessageWithError(it.isNotEmpty() && it.isNotBlank())
		})

		adapterObserver = object: RecyclerView.AdapterDataObserver() {
			override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
				mainFragmentRecyclerView.scrollToPosition(positionStart)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_main, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		if (savedInstanceState == null) {
			mainFragmentRecyclerView.adapter = photosAdapter
			WZViewUtils.setRecyclerLayoutAnimation(activityContext, mainFragmentRecyclerView)
			mainFragmentPoweredByContainer.setOnClickListener { openPexelsWebsite() }
		}
	}

	override fun onSearchQuerySubmit(query: String) {
		if (query.isNotBlank()) {
			submitQuery(query)
		}
	}

	private fun submitQuery(query: String) {
		if (ConnectivityUtils.isNetworkConnected(activityContext)) {
			val queryRequest = RequestResponseModels.ViewModelQueryRequest(query)
			photoViewModel.getPhotosWithQuery(queryRequest)
			showHideMainProgressBar(true)
		} else {
			Toast.makeText(activityContext, getString(R.string.main_fragment_toast_message_no_network), Toast.LENGTH_LONG).show()
		}
	}

	private fun openPexelsWebsite() {
		openUrlInBrowser(activityContext, Constants.URL_PEXELS_SITE)
	}

	private fun showHideEmptyMessageWithError(show: Boolean) {
		when (show) {
			true -> {
				mainFragmentEmptyMessageTextView.text = getString(R.string.main_fragment_empty_message_network_error)
				mainFragmentEmptyMessageTextView.visibility = View.VISIBLE
				mainFragmentRecyclerView.visibility = View.GONE
			}
			else -> {
				mainFragmentEmptyMessageTextView.text = getString(R.string.main_fragment_empty_message_default)
				mainFragmentEmptyMessageTextView.visibility = View.GONE
				mainFragmentRecyclerView.visibility = View.VISIBLE
			}
		}
		showHideMainProgressBar(false)
	}

	private fun showHideMainProgressBar(show: Boolean) {
		DLog.i("showHideMainProgressBar")
		when (show) {
			true -> mainFragmentProgressBar.visibility = View.VISIBLE
			else -> mainFragmentProgressBar.visibility = View.GONE
		}

	}
}