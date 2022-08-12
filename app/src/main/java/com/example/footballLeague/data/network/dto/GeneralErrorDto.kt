package com.example.footballLeague.data.network.dto

import com.google.gson.annotations.SerializedName

data class GeneralErrorDto(
    @SerializedName("status")
    val status:String,
    @SerializedName("code")
    val code:String,
    @SerializedName("message")
    val message:String
)