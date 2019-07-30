package com.alikazi.codetest.weatherzone.models

import com.google.gson.annotations.SerializedName

data class QueryResponse(
	@SerializedName("total_results")
	var totalResults: Int = 0,
	var page: Int = 0,
	@SerializedName("per_page")
	var perPage: Int = 0,
	var photos: List<Photo> = emptyList()
)