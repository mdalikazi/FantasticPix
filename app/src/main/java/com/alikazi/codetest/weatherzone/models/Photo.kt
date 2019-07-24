package com.alikazi.codetest.weatherzone.models

import com.google.gson.annotations.SerializedName

data class Photo(
    var id: Int,
    var width: Int,
    var height: Int,
    @SerializedName("url")
    var webUrl: String,
    @SerializedName("photographer")
    var photographerName: String,
    @SerializedName("photograher_url")
    var photograherUrl: String,
    var src: Src
)