package com.ermilova.android.core

import com.ermilova.android.core.Character

interface CharacterRepo {
    suspend fun getAllCharacters(): List<Character>
    suspend fun addCharacter(character: Character)
    suspend fun getCharacter(characterId: Long): Character
}