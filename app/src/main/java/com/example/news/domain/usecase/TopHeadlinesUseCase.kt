package com.example.news.domain.usecase

import com.example.news.domain.common.ApiFailure
import com.example.news.domain.common.ApiResult
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.repository.NewsDataBaseRepo
import com.example.news.domain.repository.TopHeadlinesRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(
    private val repo: TopHeadlinesRepo,
    private val dbRepo: NewsDataBaseRepo
) {

    suspend fun getEgyptNews(queryMap: HashMap<String, String>) =
        checkOnFavItems(repo.getEgyptNews(queryMap))


    suspend fun getLatestNews(queryMap: HashMap<String, String>) =
        checkOnFavItems(repo.getLatestNewsFromSources(queryMap))

    private fun checkOnFavItems(newsList: ApiResult<List<TopHeadlinesEntity>, ApiFailure>) = flow {

        val favNewsList = dbRepo.getAllFavNewsFromDB()
        if (favNewsList.isNotEmpty()) {
            favNewsList.forEach { favItems ->
                newsList.value?.forEach { news ->
                    if (favItems.title == news.title)
                        news.isFav = true
                }
            }
        }
        emit(newsList)

    }

}