package com.alikazi.codetest.weatherzone.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Photo(
    @PrimaryKey
    @NonNull
    var id: Int,
    var width: Int,
    var height: Int,
    @SerializedName("url")
    var webUrl: String = "",
    @SerializedName("photographer")
    var photographerName: String = "",
    @SerializedName("photographer_url")
    var photograherUrl: String = "",
    var src: Src,
    var searchQuery: String
)