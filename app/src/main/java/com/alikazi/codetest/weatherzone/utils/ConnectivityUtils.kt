package com.alikazi.codetest.weatherzone.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat

object ConnectivityUtils {

	fun isNetworkConnected(context: Context): Boolean {
		val connectivityManager = ContextCompat.getSystemService(
			context,
			ConnectivityManager::class.java)
				as ConnectivityManager
		val networkInfo = connectivityManager.activeNetworkInfo
		return networkInfo != null && networkInfo.isConnected
	}

}