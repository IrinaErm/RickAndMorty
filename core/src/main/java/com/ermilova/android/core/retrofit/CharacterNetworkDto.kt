package com.ermilova.android.core.retrofit

import com.squareup.moshi.Json

data class CharacterNetworkDto(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String?,

    @Json(name = "status")
    val status: String?,

    @Json(name = "species")
    val species: String?,

    @Json(name = "gender")
    val gender: String?,

    @Json(name = "created")
    val created: String?,

    @Json(name = "image")
    val image: String?,
)



