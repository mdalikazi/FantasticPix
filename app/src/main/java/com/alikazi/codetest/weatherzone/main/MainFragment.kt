package com.alikazi.codetest.weatherzone.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.*
import com.alikazi.codetest.weatherzone.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), WZSearchView.SearchViewEventsListener {

	private lateinit var photoViewModel: PhotoViewModel
	private lateinit var photosAdapter: PhotosAdapter
	private lateinit var activityContext: Context

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
		retainInstance = true

		WZSearchView.setSearchViewEventsListener(this)
		activityContext = activity!!.applicationContext
		photosAdapter = PhotosAdapter(activityContext)
		photoViewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory())
			.get(PhotoViewModel::class.java)

		photoViewModel.photos.observe(this, Observer {
			DLog.d("photos size ${it?.size}")
			photosAdapter.submitList(it)
			showEmptyMessageErrorOrNoResults(it.isNullOrEmpty())
		})

		photoViewModel.networkErrors.observe(this, Observer {
			DLog.d("error $it")
			showEmptyMessageErrorOrNoResults(it.isNotEmpty() && it.isNotBlank())
		})
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(com.alikazi.codetest.weatherzone.R.layout.fragment_main, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		if (savedInstanceState == null) {
			mainFragmentRecyclerView.adapter = photosAdapter
			mainFragmentPoweredByContainer.setOnClickListener { openPexelsWebsite() }
		}
	}

	override fun onSearchQuerySubmit(query: String) {
		if (query.isNotBlank()) {
			submitQuery(query)
			showHideEmptyMessage(false)
		}
	}

	private fun submitQuery(query: String) {
		if (ConnectivityUtils.isNetworkConnected(activityContext)) {
			val queryRequest = RequestResponseModels.ViewModelQueryRequest(query)
			photoViewModel.getPhotosWithQuery(queryRequest)
		} else {
			Toast.makeText(activityContext, getString(com.alikazi.codetest.weatherzone.R.string.main_fragment_toast_message_no_network), Toast.LENGTH_LONG).show()
		}
	}

	private fun openPexelsWebsite() {
		val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL_PEXELS_SITE))
		startActivity(browserIntent)
	}

	private fun showEmptyMessageErrorOrNoResults(show: Boolean) {
		when (show) {
			true -> {
				mainFragmentEmptyMessageTextView.text = getString(com.alikazi.codetest.weatherzone.R.string.main_fragment_empty_message_network_error)
			}
			else -> {
				mainFragmentEmptyMessageTextView.text = getString(com.alikazi.codetest.weatherzone.R.string.main_fragment_empty_message_default)
			}
		}
		showHideEmptyMessage(show)
	}

	private fun showHideEmptyMessage(show: Boolean) {
		when (show) {
			true -> {
				mainFragmentRecyclerView.visibility = View.GONE
				mainFragmentEmptyMessageTextView.visibility = View.VISIBLE
			}
			else -> {
				mainFragmentRecyclerView.visibility = View.VISIBLE
				mainFragmentEmptyMessageTextView.visibility = View.GONE
			}
		}
	}
}