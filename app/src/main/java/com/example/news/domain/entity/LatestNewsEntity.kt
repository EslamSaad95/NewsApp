package com.example.news.domain.entity

data class LatestNewsEntity(
    val imageUrl:String?="",
    val title:String?="",
    val isFav:Boolean?=false,
    val date:String?="",
    val description:String?=""
)