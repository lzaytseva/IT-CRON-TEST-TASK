package com.github.lzaytseva.it_cron_test_task.util

sealed class Resource<T>(val data: T? = null, val errorType: ErrorType? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorType: ErrorType) : Resource<T>(errorType = errorType)
}

enum class ErrorType {
    NO_INTERNET,
    SERVER_ERROR
}
