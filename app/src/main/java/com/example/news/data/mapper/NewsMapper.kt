package com.example.news.data.mapper

import com.example.news.data.network.dto.NewsDto
import com.example.news.domain.entity.EgyptNewsEntity
import com.example.news.domain.entity.LatestNewsEntity

fun List<NewsDto.Article>.toEgyptNewsEntity(): List<EgyptNewsEntity> {
    return this.map {
        EgyptNewsEntity(
            it.urlToImage,
            it.title,
            date = it.publishedAt,
            description = it.description
        )
    }
}

fun List<NewsDto.Article>.toLatestNews(): List<LatestNewsEntity> {
    return this.map { LatestNewsEntity(it.urlToImage,it.title, date = it.publishedAt, description = it.description) }
}