package com.example.mini_projet.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>,
): AppResult<T> {

    return try {
        val response: Response<T> = apiCall()
        if (response.isSuccessful) {
            val body: T? = response.body()
            AppResult.Success(body)
        } else {


            AppResult.Failure(null, "Unknown Error")
        }
    } catch (e: Exception) {
        AppResult.Error.NonRecoverableError(e)
    }
}

//
fun <T> apiCall(
    call: suspend () -> Response<T>,
): Flow<AppResult<T>> =
    flow {
        emit(AppResult.InProgress)
        emit(safeApiCall(call))
    }



