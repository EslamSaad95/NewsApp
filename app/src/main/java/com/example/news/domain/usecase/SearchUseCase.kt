package com.example.news.domain.usecase

import com.example.news.domain.repository.NewsDataBaseRepo
import com.example.news.domain.repository.SearchRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepo: SearchRepo,
    private val dataBaseRepo: NewsDataBaseRepo
) {

    suspend fun getSearchResults(queryMap: HashMap<String, String>) = flow {

        val searchResults = searchRepo.getSearchResults(queryMap)
        val favResults = dataBaseRepo.getAllFavNewsFromDB()

        if (favResults.isNotEmpty()) {
            favResults.forEach { favNews ->
                searchResults.value?.forEach { newsEntity ->
                    if (favNews.title == newsEntity.title)
                        newsEntity.isFav = true
                }

            }
        }
        emit(searchResults)
    }


}