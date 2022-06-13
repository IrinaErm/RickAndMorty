package com.ermilova.android.core.domain

import androidx.paging.PagingData
import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.domain.model.CharacterDomainModel
import kotlinx.coroutines.flow.Flow


interface CharacterRepo {
    fun getAllCharacters(): Flow<PagingData<CharacterDomainModel>>
    suspend fun cacheCharacter(vararg character: CharacterEntity)
    suspend fun getCharacter(characterId: Long): CharacterDomainModel
}