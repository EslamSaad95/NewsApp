package com.example.news.domain.repository

import com.example.news.domain.entity.TopHeadlinesEntity

interface NewsDataBaseRepo {

    suspend fun addNewsToDB(newsEntity: TopHeadlinesEntity)

    suspend fun removeNewsFromDB(newsEntity: TopHeadlinesEntity)

    suspend fun getAllFavNewsFromDB(): List<TopHeadlinesEntity>
}