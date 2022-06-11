package com.ermilova.android.core.utils

sealed class ApiStatus<out T : Any> {
    object Loading : ApiStatus<Nothing>()
    data class Loaded<out T : Any>(val list: List<T>) : ApiStatus<List<T>>()
    class Error<out T : Any>(val throwable: Throwable) : ApiStatus<T>()
}