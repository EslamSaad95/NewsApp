package com.example.news.domain.usecase

import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.repository.NewsDataBaseRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsDataBaseUseCase @Inject constructor(private val repo: NewsDataBaseRepo) {

    suspend fun addTeam(news: TopHeadlinesEntity) {
        repo.addNewsToDB(news)
    }

    fun getAllTeams() = flow { emit(repo.getAllFavNewsFromDB()) }


    suspend fun removeFromTeams(news: TopHeadlinesEntity) {
        repo.removeNewsFromDB(news)
    }
}