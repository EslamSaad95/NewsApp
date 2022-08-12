package com.example.footballLeague.domain.usecase

import com.example.footballLeague.domain.repository.TopHeadlinesRepo
import retrofit2.http.QueryMap
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(private val repo:TopHeadlinesRepo) {

    suspend fun getEgyptNews(queryMap: HashMap<String,String>)=repo.getEgyptNews(queryMap)
}