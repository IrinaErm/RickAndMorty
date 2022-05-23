package com.ermilova.android.core.retrofit

import com.squareup.moshi.Json

data class CharactersList(
    @Json(name = "results")
    val results: List<CharacterNetworkDto>
)