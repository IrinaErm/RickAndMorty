package com.ermilova.android.core.data.retrofit

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterApi: CharacterRetrofitApi) {
    suspend fun getCharacters(): CharacterNetworkDtoContainer {
        return characterApi.getCharacters()
    }
}