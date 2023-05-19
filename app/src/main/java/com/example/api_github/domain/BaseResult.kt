package com.example.api_github.domain

import retrofit2.Response
import java.net.HttpURLConnection

sealed class BaseResult<out Response> {
    data class Success<Response>(val value: Response) : BaseResult<Response>()
    data class Failure(val statusCode: Int) : BaseResult<Nothing>()
}

fun <R : Any> Response<R>.parseResponse(): BaseResult<R> {
    if (isSuccessful) {
        val body = body()

        if (body != null) {
            return BaseResult.Success(body)
        }
    } else {
        return BaseResult.Failure(code())
    }
    return BaseResult.Failure(HttpURLConnection.HTTP_INTERNAL_ERROR)
}