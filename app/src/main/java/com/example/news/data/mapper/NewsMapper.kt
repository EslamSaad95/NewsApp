package com.example.news.data.mapper

import com.example.news.data.network.dto.NewsDto
import com.example.news.domain.entity.TopHeadlinesEntity



fun List<NewsDto.Article>.toLatestNews(): List<TopHeadlinesEntity> {
    return this.map { TopHeadlinesEntity(it.urlToImage,it.title, date = it.publishedAt, description = it.description) }
}