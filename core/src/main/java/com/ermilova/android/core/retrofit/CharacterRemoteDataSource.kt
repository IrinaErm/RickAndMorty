package com.ermilova.android.core.retrofit

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterApi: CharacterRetrofitApi) {
    suspend fun getAllCharacters(): CharactersList {
        return characterApi.getAllCharacters()
    }
}