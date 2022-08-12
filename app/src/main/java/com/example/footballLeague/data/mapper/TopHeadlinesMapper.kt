package com.example.footballLeague.data.mapper

import com.example.footballLeague.data.network.dto.NewsDto
import com.example.footballLeague.domain.entity.EgyptNewsEntity

fun List<NewsDto.Article>.toEgyptNewsEntity(): List<EgyptNewsEntity> {
    return this.map { EgyptNewsEntity(it.title, it.urlToImage) }
}