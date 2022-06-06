package com.ermilova.android.core

import com.ermilova.android.core.retrofit.CharacterNetworkDto
import com.ermilova.android.core.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.room.CharacterLocalDataSource
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) : CharacterRepo {

    override suspend fun getAllCharacters(): List<Character> {
        return characterRemoteDataSource.getAllCharacters().map { characterNetworkDto ->
            CharacterNetworkDto.mapToModel(characterNetworkDto).also { character ->
                cacheCharacter(character)
            }
        }
    }

    override suspend fun cacheCharacter(character: Character) {
        characterLocalDataSource.saveCharacter(character)
    }

    override suspend fun getCharacter(characterId: Long): Character {
        return characterLocalDataSource.getCharacter(characterId)
    }
}