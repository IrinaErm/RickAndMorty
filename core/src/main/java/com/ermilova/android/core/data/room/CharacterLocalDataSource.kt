package com.ermilova.android.core.data.room

import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(private val characterDao: CharacterDao) {
    suspend fun saveCharacter(vararg character: CharacterEntity) {
        characterDao.insertCharacter(*character)
    }

    suspend fun getCharacter(characterId: Long): CharacterEntity {
        return characterDao.getCharacter(characterId)
    }
}