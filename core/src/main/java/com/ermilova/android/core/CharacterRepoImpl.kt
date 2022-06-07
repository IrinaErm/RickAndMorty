package com.ermilova.android.core

import com.ermilova.android.core.domain.CharacterModel
import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.retrofit.toDatabaseEntity
import com.ermilova.android.core.retrofit.toDomainModel
import com.ermilova.android.core.room.CharacterEntity
import com.ermilova.android.core.room.CharacterLocalDataSource
import com.ermilova.android.core.room.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) : CharacterRepo {

    override suspend fun getAllCharacters(): List<CharacterModel> {
        return withContext(Dispatchers.IO) {
            val characters = characterRemoteDataSource.getCharacters()
            cacheCharacter(*characters.toDatabaseEntity())
            characters.toDomainModel()
        }
    }

    override suspend fun cacheCharacter(vararg character: CharacterEntity) {
        withContext(Dispatchers.IO) {
            characterLocalDataSource.saveCharacter(*character)
        }
    }

    override suspend fun getCharacter(characterId: Long): CharacterModel {
        return withContext(Dispatchers.IO) {
            characterLocalDataSource.getCharacter(characterId).toDomainModel()
        }
    }
}