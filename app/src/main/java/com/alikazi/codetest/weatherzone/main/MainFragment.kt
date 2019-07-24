package com.alikazi.codetest.weatherzone.main

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alikazi.codetest.weatherzone.R
import com.alikazi.codetest.weatherzone.models.RequestResponseModels
import com.alikazi.codetest.weatherzone.utils.Constants.LOG_TAG
import com.alikazi.codetest.weatherzone.utils.DLog
import com.alikazi.codetest.weatherzone.utils.Injector
import com.alikazi.codetest.weatherzone.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.custom_appbar.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var photoViewModel: PhotoViewModel
	private lateinit var photosAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
	    retainInstance = true
	    setHasOptionsMenu(true)

	    photosAdapter = PhotosAdapter(activity)
	    photoViewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory())
            .get(PhotoViewModel::class.java)

	    photoViewModel.photos.observe(this, Observer {
		    DLog.d("photos size ${it?.size}")
		    photosAdapter.submitList(it)
	    })

	    photoViewModel.networkErrors.observe(this, Observer {
		    DLog.d("error $it")
		    mainFragmentEmptyMessageTextView.text = getString(R.string.main_fragment_empty_message_network_error)
		    showHideEmptyMessage(it.isNotEmpty() && it.isNotBlank())
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
			    photoViewModel.getPhotosWithQuery(queryRequest)
		    }
	    }
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

	private fun setupSearchViewRevealToolbar() {
		revealToolbar.inflateMenu(R.menu.menu_main)
		mSearchMenuItem = revealToolbar.menu.findItem(R.id.menu_action_search)
		val searchView = mSearchMenuItem.getActionView() as SearchView
		searchView.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener() {
			fun onQueryTextSubmit(query: String): Boolean {
				Log.d(LOG_TAG, "onQueryTextChange newText: $query")
				mSearchSubmitted = true
				processSearch(query)
				searchView.clearFocus()
				return true
			}

			fun onQueryTextChange(newText: String): Boolean {
				// Disallow
				return false
			}
		})

		mSearchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
			override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
				animateSearchView(true)
				return true
			}

			override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
				animateSearchView(false)
				return false
			}
		})
	}

	fun onCreateOptionsMenu(menu: Menu): Boolean {
		MenuInflater().inflate(R.menu.menu_main, menu)
		return true
	}

	fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.menu_home_search_icon -> {
				animateSearchView(mSearchViewRevealAppBar.getVisibility() !== View.VISIBLE)
				mSearchMenuItem.expandActionView()
			}
		}
		return true
	}

	private fun animateSearchView(reveal: Boolean) {
		Log.i(LOG_TAG, "animateSearchView")
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			AnimationUtils.circleReveal(this, mSearchViewRevealAppBar, 0, true, reveal)
		} else {
			TransitionManager.beginDelayedTransition(findViewById(R.id.toolbar) as ViewGroup)
			mSearchViewRevealAppBar.setVisibility(if (reveal) View.VISIBLE else View.INVISIBLE)
		}
	}

}