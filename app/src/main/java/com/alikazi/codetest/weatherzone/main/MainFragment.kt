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

class MainFragment : Fragment() {

    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoViewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory())
            .get(PhotoViewModel::class.java)

	    photoViewModel.photos.observe(this, Observer {
		    DLog.d("photos size ${it?.size}")
	    })

	    photoViewModel.networkErrors.observe(this, Observer {
		    DLog.d("error $it")
	    })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonGo.setOnClickListener {
            var queryRequest = RequestResponseModels.ViewModelQueryRequest(textViewQuery.text.toString())
            photoViewModel.getPhotosWithQuery(queryRequest)
        }
    }
}