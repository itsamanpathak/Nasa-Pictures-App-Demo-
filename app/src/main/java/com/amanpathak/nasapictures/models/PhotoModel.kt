package com.amanpathak.nasapictures.models

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    val title: String,
    @SerializedName("url")
    val thumbnail: String,
    @SerializedName("hdurl")
    val url: String,
    @SerializedName("media_type")
    val mediaType: String,
    val copyright: String,
    val date: String,
    val explanation: String,
    @SerializedName("service_version")
    val serviceVersion: String
)
