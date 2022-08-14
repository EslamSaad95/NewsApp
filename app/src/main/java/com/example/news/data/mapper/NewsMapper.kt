package com.example.news.data.mapper

import com.example.news.data.network.dto.NewsDto
import com.example.news.data.room.NewsDatabaseEntity
import com.example.news.domain.entity.TopHeadlinesEntity


fun List<NewsDto.Article>.toLatestNews(): List<TopHeadlinesEntity> {
    return this.map {
        TopHeadlinesEntity(
            it.urlToImage,
            it.title,
            date = it.publishedAt,
            description = it.description
        )
    }
}


fun NewsDatabaseEntity.toTopHeadlinesEntity(): TopHeadlinesEntity {
    return TopHeadlinesEntity(
        this.newsImageUrl,
        this.newsTitle,
        this.newsIsFav,
        this.newsDate,
        this.newsDescription
    )
}

fun TopHeadlinesEntity.toNewsDataBaseEntity(): NewsDatabaseEntity {
    return NewsDatabaseEntity(
        newsDate = this.date, newsDescription = this.description, newsImageUrl = this.imageUrl,
        newsTitle = this.title, newsIsFav = this.isFav,
    )


}


fun List<NewsDatabaseEntity>.toTopHeadlines(): List<TopHeadlinesEntity> {
    return this.map {
        TopHeadlinesEntity(
            it.newsImageUrl,
            it.newsTitle,
            it.newsIsFav,
            it.newsDate,
            it.newsDescription
        )
    }
}


