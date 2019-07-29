package com.alikazi.codetest.weatherzone.database

import com.alikazi.codetest.weatherzone.models.Src
import com.google.gson.Gson

class WZTypeConverters {

	fun convertStringToSrc(string: String): Src {
		return Gson().fromJson(string, Src::class.java)
	}

	fun convertSrcToString(src: Src): String {
		return Gson().toJson(src)
	}

}