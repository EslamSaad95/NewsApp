package com.example.news.domain.common

import com.example.news.R

sealed class ApiFailure(var error: String? = null, var errorResId: Int? = null) {

    data class InvalidInput(
        val errorMessageResId: Int = R.string.network_invalid_input
    ) : ApiFailure(errorResId = errorMessageResId)

    data class ApiError(
        var errorMessage: String? = null
    ) : ApiFailure(error = errorMessage)

    data class UnAuthorizedAccess(
        val errorMessageResId: Int = R.string.network_unauthorized
    ) : ApiFailure(errorResId = errorMessageResId)

    data class Forbidden(
        val errorMessageResId: Int = R.string.network_forbidden
    ) : ApiFailure(errorResId = errorMessageResId)

    data class NotFound(
        val errorMessageResId: Int = R.string.network_not_found
    ) : ApiFailure(errorResId = errorMessageResId)

    data class ServerError(
        val errorMessageResId: Int = R.string.network_server_error
    ) : ApiFailure(errorResId = errorMessageResId)

    data class TimeOut(
        val errorMessageResId: Int = R.string.network_time_out
    ) : ApiFailure(errorResId = errorMessageResId)

    data class ConnectionError(
        val errorMessageResId: Int = R.string.network_connection_error
    ) : ApiFailure(errorResId = errorMessageResId)

    data class unexpectedResponse(
        val errorMessageResId: Int = R.string.network_unexpected_response
    ) : ApiFailure(errorResId = errorMessageResId)

    data class UnKnownError(
        val errorMessageResId: Int = R.string.network_unknown_error
    ) : ApiFailure(errorResId = errorMessageResId)
}