package com.example.news.domain.usecase

import com.example.news.domain.repository.SearchRepo
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepo: SearchRepo) {

    suspend fun getSearchResults(queryMap:HashMap<String,String>)=searchRepo.getSearchResults(queryMap)
}