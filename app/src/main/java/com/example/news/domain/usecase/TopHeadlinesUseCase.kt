package com.example.news.domain.usecase

import com.example.news.domain.repository.TopHeadlinesRepo
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(private val repo:TopHeadlinesRepo) {

    suspend fun getEgyptNews(queryMap: HashMap<String,String>)=repo.getEgyptNews(queryMap)


    suspend fun getLatestNews(queryMap: HashMap<String,String>)=repo.getLatestNewsFromSources(queryMap)
}