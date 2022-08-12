package com.example.footballLeague.domain.repository

import com.example.footballLeague.domain.entity.EgyptNewsEntity
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.common.ApiResult

interface TopHeadlinesRepo {

    suspend fun getEgyptNews(queryMap:HashMap<String,String>):ApiResult<List<EgyptNewsEntity>,ApiFailure>
}