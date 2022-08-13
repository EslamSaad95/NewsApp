package com.example.news.domain.entity

data class EgyptNewsEntity(
    val imageUrl:String?="",
    val title:String?="",
    val isFav:Boolean?=false,
    val date:String?=""
)