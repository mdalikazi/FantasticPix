package com.alikazi.codetest.weatherzone.models

import com.google.gson.annotations.SerializedName

data class QueryResponse(
	@SerializedName("total_results")
	var totalResults: Int,
	var page: Int,
	@SerializedName("per_page")
	var perPage: Int,
	var photos: List<Photo> = emptyList()
)