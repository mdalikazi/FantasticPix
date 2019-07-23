package com.alikazi.codetest.weatherzone.models

import com.google.gson.annotations.SerializedName

data class Photo(
    var id: Int,
    var width: Int,
    var height: Int,
    var url: String,
    var photographer: String,
    @SerializedName("photograher_url")
    var photograherUrl: String,
    var src: List<Src> = emptyList()
)