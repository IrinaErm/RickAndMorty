package com.ermilova.android.core.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterRetrofitApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") currentPage: Int = 1): CharacterNetworkDtoContainer
}