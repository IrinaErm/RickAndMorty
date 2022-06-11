package com.ermilova.android.core.data

import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.data.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.data.retrofit.toDatabaseEntity
import com.ermilova.android.core.data.retrofit.toDomainModel
import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.data.room.CharacterLocalDataSource
import com.ermilova.android.core.data.room.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) : CharacterRepo {

    override suspend fun getAllCharacters(): List<CharacterDomainModel> {
        return withContext(Dispatchers.IO) {
            characterRemoteDataSource.getCharacters().let { characterNetworkDtoContainer ->
                cacheCharacter(*characterNetworkDtoContainer.toDatabaseEntity())
                characterNetworkDtoContainer.toDomainModel()
            }
        }
    }

    override suspend fun cacheCharacter(vararg character: CharacterEntity) {
        withContext(Dispatchers.IO) {
            characterLocalDataSource.saveCharacter(*character)
        }
    }

    override suspend fun getCharacter(characterId: Long): CharacterDomainModel {
        return withContext(Dispatchers.IO) {
            characterLocalDataSource.getCharacter(characterId).toDomainModel()
        }
    }
}