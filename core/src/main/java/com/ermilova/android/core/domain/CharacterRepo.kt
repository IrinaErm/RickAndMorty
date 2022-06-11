package com.ermilova.android.core.domain

import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.domain.model.CharacterDomainModel

interface CharacterRepo {
    suspend fun getAllCharacters(): List<CharacterDomainModel>
    suspend fun cacheCharacter(vararg character: CharacterEntity)
    suspend fun getCharacter(characterId: Long): CharacterDomainModel
}