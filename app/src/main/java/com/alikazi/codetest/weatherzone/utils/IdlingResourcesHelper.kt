package com.alikazi.codetest.weatherzone.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResourcesHelper {

    private const val RESOURCE = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource {
        return countingIdlingResource
    }

}