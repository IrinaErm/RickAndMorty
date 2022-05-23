package com.ermilova.android.core.room

import com.ermilova.android.core.Character
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(private val characterDao: CharacterDao) {
    suspend fun saveCharacter(character: Character) {
        characterDao.addCharacter(character)
    }

    suspend fun getCharacter(characterId: Long): Character {
        return characterDao.getCharacter(characterId)
    }
}