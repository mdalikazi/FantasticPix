package com.alikazi.codetest.weatherzone.utils

import android.util.Log
import com.alikazi.codetest.weatherzone.BuildConfig

object DLog {

	private val isLogcatEnabled = BuildConfig.DEBUG

	private val sourceCodeFileName: String
		get() = Thread.currentThread().stackTrace[5].fileName

	private val lineNumber: Int
		get() = Thread.currentThread().stackTrace[5].lineNumber

	private fun getTextWithSource(text: String): String {
		return "<$sourceCodeFileName:$lineNumber> $text"
	}

	fun i(msg: String) {
		if (isLogcatEnabled) {
			Log.i(Constants.LOG_TAG, getTextWithSource(msg))
		}
	}

	fun d(msg: String) {
		if (isLogcatEnabled) {
			Log.d(Constants.LOG_TAG, getTextWithSource(msg))
		}
	}

	fun w(tag: String, msg: String) {
		if (isLogcatEnabled) {
			Log.w(Constants.LOG_TAG, getTextWithSource(msg))
		}
	}

	fun e(tag: String, msg: String) {
		if (isLogcatEnabled) {
			Log.e(Constants.LOG_TAG, getTextWithSource(msg))
		}
	}
}