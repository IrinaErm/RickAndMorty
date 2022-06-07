package com.ermilova.android.core.retrofit

import retrofit2.http.GET

interface CharacterRetrofitApi {
    @GET("character")
    suspend fun getCharacters(): CharacterNetworkDtoContainer
}