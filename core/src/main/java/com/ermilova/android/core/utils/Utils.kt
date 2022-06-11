package com.ermilova.android.core.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun parseCharacterCreated(createdDate: String?): String? {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    var date: String? = null
    try {
        date = createdDate?.let { created ->
            simpleDateFormat.parse(created)?.let { createdDate ->
                formatter.format(createdDate)
            }
        }
    } catch(e: Exception) {
        Log.e("DateParseException", e.toString())
    }
    return date
}