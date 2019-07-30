package com.alikazi.codetest.weatherzone.database

import androidx.room.TypeConverter
import com.alikazi.codetest.weatherzone.models.Src
import com.google.gson.Gson

class WZTypeConverters {

	@TypeConverter
	fun convertStringToSrc(string: String): Src {
		return Gson().fromJson(string, Src::class.java)
	}

	@TypeConverter
	fun convertSrcToString(src: Src): String {
		return Gson().toJson(src)
	}

}