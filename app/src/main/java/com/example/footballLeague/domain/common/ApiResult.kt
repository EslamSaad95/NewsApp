package com.example.news.domain.common

data class ApiResult<T, E>(
    val value: T? = null,
    val error: E? = null
)