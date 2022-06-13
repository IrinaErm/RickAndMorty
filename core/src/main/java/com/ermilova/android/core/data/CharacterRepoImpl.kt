package com.ermilova.android.core.data

import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.data.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.data.retrofit.toDatabaseEntity
import com.ermilova.android.core.data.retrofit.toDomainModel
import com.ermilova.android.core.data.room.CharacterEntity
import com.ermilova.android.core.data.room.CharacterLocalDataSource
import com.ermilova.android.core.data.room.toDomainModel
import com.ermilova.android.core.utils.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) : CharacterRepo {

    override fun getAllCharacters(): Flow<ApiStatus<List<CharacterDomainModel>>> {
        return flow {
            try {
                characterRemoteDataSource.getCharacters().let { characterNetworkDtoContainer ->
                    cacheCharacter(*characterNetworkDtoContainer.toDatabaseEntity())
                    emit(ApiStatus.Loaded(characterNetworkDtoContainer.toDomainModel()))
                }
            } catch (e: Exception) {
                emit(ApiStatus.Error(e))
            }
        }.flowOn(Dispatchers.IO)
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