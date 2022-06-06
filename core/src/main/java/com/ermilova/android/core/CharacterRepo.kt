package com.ermilova.android.core


interface CharacterRepo {
    suspend fun getAllCharacters(): List<Character>
    suspend fun cacheCharacter(character: Character)
    suspend fun getCharacter(characterId: Long): Character
}