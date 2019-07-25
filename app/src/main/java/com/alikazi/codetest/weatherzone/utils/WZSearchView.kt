package com.alikazi.codetest.weatherzone.utils

import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.alikazi.codetest.weatherzone.R
import com.google.android.material.appbar.AppBarLayout

class WZSearchView(private val context: Context,
                   private val menu: Menu?,
                   private val revealAppBar: AppBarLayout) {

	init { initSearchView() }

	private var searchView: SearchView? = null
	var searchMenuItem: MenuItem? = null
	private var searchViewEventsListener: SearchViewEventsListener? = null

	private fun initSearchView() {
		searchMenuItem = menu?.findItem(R.id.menu_action_search)
		searchMenuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
		searchView = searchMenuItem?.actionView as SearchView?
		searchView?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
		searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				Log.d(Constants.LOG_TAG, "onQueryTextChange newText: $query")
				searchViewEventsListener?.onSearchQuerySubmit(query)
				searchView?.clearFocus()
				return true
			}

			override fun onQueryTextChange(p0: String?): Boolean {
				// Disallow, we have no use of this right now
				return false
			}
		})

		searchMenuItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
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

	fun animateSearchView(reveal: Boolean) {
		Log.i(Constants.LOG_TAG, "animateSearchView")
		WZAnimationUtils.circularReveal(context, revealAppBar, 0, true, reveal)
	}

	fun setSearchViewEventsListener(searchViewEventsListener: SearchViewEventsListener) {
		this.searchViewEventsListener = searchViewEventsListener
	}

	fun getSearchView(): SearchView? {
		return searchView
	}

	interface SearchViewEventsListener {
		fun onSearchQuerySubmit(query: String)
	}

}