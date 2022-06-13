package com.ermilova.android.core.domain

import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.utils.ApiStatus
import kotlinx.coroutines.flow.Flow

interface CharacterRepo {
    fun getAllCharacters(): Flow<ApiStatus<List<CharacterDomainModel>>>
    suspend fun cacheCharacter(vararg character: CharacterEntity)
    suspend fun getCharacter(characterId: Long): CharacterDomainModel
}