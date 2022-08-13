package com.example.news.domain.repository

import com.example.news.domain.common.ApiFailure
import com.example.news.domain.common.ApiResult
import com.example.news.domain.entity.TopHeadlinesEntity
import retrofit2.http.QueryMap

interface SearchRepo {
    suspend fun getSearchResults(queryMap: HashMap<String,String>):ApiResult<List<TopHeadlinesEntity>,ApiFailure>
}