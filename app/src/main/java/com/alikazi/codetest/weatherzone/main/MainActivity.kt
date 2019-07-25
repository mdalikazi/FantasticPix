package com.alikazi.codetest.weatherzone.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.test.espresso.IdlingResource
import com.alikazi.codetest.weatherzone.R
import com.alikazi.codetest.weatherzone.utils.IdlingResourcesHelper
import com.alikazi.codetest.weatherzone.utils.WZSearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var customSearchView: WZSearchView

    @VisibleForTesting
    fun getIdlingResource(): IdlingResource {
        return IdlingResourcesHelper.getIdlingResource()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        setupSearchViewAndRevealToolbar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSearchViewAndRevealToolbar() {
        customSearchView = WZSearchView(this, revealToolbar)
        revealToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_main_search_icon -> {
            customSearchView.animateSearchView(revealToolbar.visibility != View.VISIBLE)
            customSearchView.getSearchMenuItem()?.expandActionView()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (revealToolbar.visibility == View.VISIBLE) {
            customSearchView.animateSearchView(false)
        } else {
            super.onBackPressed()
        }
    }
}
