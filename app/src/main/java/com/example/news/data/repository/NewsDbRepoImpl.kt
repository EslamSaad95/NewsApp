package com.example.news.data.repository

import com.example.news.data.mapper.toNewsDataBaseEntity
import com.example.news.data.mapper.toTopHeadlines
import com.example.news.data.room.NewsDao
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.repository.NewsDataBaseRepo
import javax.inject.Inject

class NewsDbRepoImpl @Inject constructor(private val newsDao: NewsDao) : NewsDataBaseRepo {

    override suspend fun addNewsToDB(newsEntity: TopHeadlinesEntity) {
        return newsDao.insertTeam(newsEntity.toNewsDataBaseEntity())
    }


    override suspend fun removeNewsFromDB(newsEntity: TopHeadlinesEntity) {
        return newsDao.delete(newsEntity.toNewsDataBaseEntity())
    }


    override suspend fun getAllFavNewsFromDB(): List<TopHeadlinesEntity> {
        return newsDao.getAllTeams().toTopHeadlines()
    }

}