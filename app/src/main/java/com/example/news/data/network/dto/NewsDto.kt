package com.example.news.data.network.dto


import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Article>
) {
    data class Article(
        @SerializedName("source")
        val source: Source,
        @SerializedName("author")
        val author: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlToImage")
        val urlToImage: String,
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("content")
        val content: Any
    ) {
        data class Source(
            @SerializedName("id")
            val id: Any,
            @SerializedName("name")
            val name: String
        )
    }
}