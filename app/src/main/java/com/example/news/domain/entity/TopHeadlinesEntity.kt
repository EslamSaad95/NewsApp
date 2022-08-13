package com.example.news.domain.entity

import android.os.Parcelable

@Parcelize
data class TopHeadlinesEntity(
    val imageUrl:String?="",
    val title:String?="",
    val isFav:Boolean?=false,
    val date:String?="",
    val description:String?=""
):Parcelable