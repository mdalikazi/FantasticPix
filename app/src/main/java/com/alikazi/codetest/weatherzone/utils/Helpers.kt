package com.alikazi.codetest.weatherzone.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

object Helpers {

	fun openUrlInBrowser(context: Context, url: String?) {
		val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
		browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
		ContextCompat.startActivity(context, browserIntent, null)
	}
}