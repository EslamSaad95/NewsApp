package com.example.news.data.repository

import com.example.news.common.map
import com.example.news.data.mapper.toLatestNews
import com.example.news.data.network.ApiService
import com.example.news.data.network.dto.GeneralErrorDto
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.common.ApiResult
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.repository.SearchRepo
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(private val apiService: ApiService) : SearchRepo {
    override suspend fun getSearchResults(queryMap: HashMap<String, String>): ApiResult<List<TopHeadlinesEntity>, ApiFailure> {
        try {
            val response = apiService.getTopHeadlinesNews(queryMap)
            if (response.isSuccessful)
                return ApiResult(value = response.body()?.articles?.toLatestNews())
            else {
                val error = Gson().fromJson(
                    response.errorBody()!!.charStream(),
                    GeneralErrorDto::class.java
                )
                error?.let {
                    return ApiResult(error = ApiFailure.ApiError(it.message))
                }
                    ?: throw HttpException(response)

            }
        } catch (throwable: Throwable) {
            return ApiResult(error = throwable.map())
        }
    }

}