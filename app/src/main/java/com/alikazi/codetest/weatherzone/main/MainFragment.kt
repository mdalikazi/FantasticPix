package com.alikazi.codetest.weatherzone.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alikazi.codetest.weatherzone.R
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.DLog
import com.alikazi.codetest.weatherzone.utils.Injector
import com.alikazi.codetest.weatherzone.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.Executors

class MainFragment : Fragment() {

    private lateinit var photoViewModel: PhotoViewModel
	private lateinit var photosAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

	    photosAdapter = PhotosAdapter(activity)
	    photoViewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory())
            .get(PhotoViewModel::class.java)

	    photoViewModel.photos.observe(this, Observer {
		    DLog.d("photos size ${it?.size}")
		    photosAdapter.submitList(it)
	    })

	    photoViewModel.networkErrors.observe(this, Observer {
		    DLog.d("error $it")
	    })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
	    if (savedInstanceState == null) {
		    mainFragmentRecyclerView.adapter = photosAdapter
		    buttonGo.setOnClickListener {
			    var queryRequest = RequestResponseModels.ViewModelQueryRequest(textViewQuery.text.toString())
			    Executors.newSingleThreadExecutor().execute {
				    photoViewModel.getPhotosWithQuery(queryRequest)
			    }
		    }
	    }
    }
}