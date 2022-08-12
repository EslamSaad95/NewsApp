package com.example.news.data.mapper

import com.example.news.data.network.dto.NewsDto
import com.example.news.domain.entity.EgyptNewsEntity

fun List<NewsDto.Article>.toEgyptNewsEntity(): List<EgyptNewsEntity> {
    return this.map { EgyptNewsEntity(it.title, it.urlToImage, date = it.publishedAt) }
}