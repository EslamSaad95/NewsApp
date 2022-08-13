package com.example.news.domain.repository

import com.example.news.domain.common.ApiFailure
import com.example.news.domain.common.ApiResult
import com.example.news.domain.entity.TopHeadlinesEntity

interface TopHeadlinesRepo {

    suspend fun getEgyptNews(queryMap:HashMap<String,String>):ApiResult<List<TopHeadlinesEntity>,ApiFailure>

    suspend fun getLatestNewsFromSources(queryMap: HashMap<String, String>):ApiResult<List<TopHeadlinesEntity>,ApiFailure>
}