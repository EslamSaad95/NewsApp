package com.example.news.data.repository

import com.example.news.data.mapper.toEgyptNewsEntity
import com.example.news.data.network.dto.GeneralErrorDto
import com.example.news.domain.entity.EgyptNewsEntity
import com.example.news.domain.repository.TopHeadlinesRepo
import com.example.news.common.map
import com.example.news.data.network.ApiService
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.common.ApiResult
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class TopHeadlinesRepoImpl @Inject constructor (private val apiService: ApiService):TopHeadlinesRepo {
    override suspend fun getEgyptNews(queryMap: HashMap<String, String>): ApiResult<List<EgyptNewsEntity>, ApiFailure> {
        try {
            val response = apiService.getTopHeadlinesNews(queryMap)
            if (response.isSuccessful)
                return ApiResult(value = response.body()?.articles?.toEgyptNewsEntity())
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