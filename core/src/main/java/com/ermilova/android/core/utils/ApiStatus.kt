package com.ermilova.android.core.utils

sealed class ApiStatus {
    object Loading: ApiStatus()
    class Loaded(val list: List<Any>): ApiStatus()
    class Error(val throwable: Throwable): ApiStatus()
}