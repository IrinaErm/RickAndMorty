package com.ermilova.android.core.utils

import android.util.Log
import java.text.SimpleDateFormat

fun parseCharacterCreated(created: String?): String? {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    var date: String? = null
    try {
        date = created?.let { created ->
            simpleDateFormat.parse(created)?.let { createdDate ->
                formatter.format(createdDate)
            }
        }
    } catch(e: Exception) {
        Log.e("DateParseException", e.toString())
    }
    return date
}