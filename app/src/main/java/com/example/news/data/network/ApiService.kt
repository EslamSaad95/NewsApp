package com.example.news.data.network

import com.example.news.data.network.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService
{
    @GET("top-headlines")
    suspend fun getTopHeadlinesNews(@QueryMap queryMap:HashMap<String,String>):Response<NewsDto>
}