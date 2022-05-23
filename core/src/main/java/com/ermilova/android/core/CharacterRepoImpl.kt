package com.ermilova.android.core

import com.ermilova.android.core.retrofit.CharacterNetworkDto
import com.ermilova.android.core.retrofit.CharacterRemoteDataSource
import com.ermilova.android.core.room.CharacterLocalDataSource
import com.ermilova.android.core.Character
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource,
) : CharacterRepo {

    override suspend fun getAllCharacters(): List<Character> {
        return characterRemoteDataSource.getAllCharacters().results.map { characterDto ->
            CharacterNetworkDto.mapToModel(characterDto).also { character ->
                characterLocalDataSource.saveCharacter(character)
            }
        }
    }

    override suspend fun addCharacter(character: Character) {
        characterLocalDataSource.saveCharacter(character)
    }

    override suspend fun getCharacter(characterId: Long): Character {
        return characterLocalDataSource.getCharacter(characterId)
    }
}