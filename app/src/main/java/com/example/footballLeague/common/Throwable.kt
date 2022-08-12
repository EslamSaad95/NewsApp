package com.example.news.common

import android.util.Log
import com.example.news.domain.common.ApiFailure
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

fun Throwable.map(): ApiFailure {
    Log.e("okhttp.OkHttpClient", stackTraceToString())
    try {
        return when (this) {
            is HttpException -> when (code()) {
                400 -> ApiFailure.InvalidInput()
                401 -> ApiFailure.UnAuthorizedAccess()
                403 -> ApiFailure.Forbidden()
                404 -> ApiFailure.NotFound()
                500, 503 -> ApiFailure.ServerError()
                else -> ApiFailure.UnKnownError()
            }
            is SocketTimeoutException -> ApiFailure.TimeOut()
            is SocketException, is IOException -> ApiFailure.ConnectionError()
            is RuntimeException -> ApiFailure.unexpectedResponse()
            else -> ApiFailure.UnKnownError()
        }
    } catch (e: Exception) {
        return ApiFailure.UnKnownError()
    }
}