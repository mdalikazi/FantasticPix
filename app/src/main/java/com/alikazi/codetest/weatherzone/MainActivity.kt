package com.alikazi.codetest.weatherzone

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.test.espresso.IdlingResource

class MainActivity : AppCompatActivity() {

//    @VisibleForTesting
//    fun getIdlingResource(): IdlingResource {
//        return IdlingResourcesHelper.getIdlingResource()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitNow()
        }
    }
}
