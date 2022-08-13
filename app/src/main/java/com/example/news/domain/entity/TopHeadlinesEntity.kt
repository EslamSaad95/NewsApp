package com.example.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopHeadlinesEntity(
    val imageUrl:String?="",
    val title:String?="",
    val isFav:Boolean?=false,
    val date:String?="",
    val description:String?=""
):Parcelable