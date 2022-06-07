package com.ermilova.android.core.domain

import com.ermilova.android.core.domain.CharacterModel
import com.ermilova.android.core.room.CharacterEntity


interface CharacterRepo {
    suspend fun getAllCharacters(): List<CharacterModel>
    suspend fun cacheCharacter(vararg character: CharacterEntity)
    suspend fun getCharacter(characterId: Long): CharacterModel
}