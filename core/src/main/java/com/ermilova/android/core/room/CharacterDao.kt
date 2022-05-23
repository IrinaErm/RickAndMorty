package com.ermilova.android.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ermilova.android.core.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(character: Character)

    @Query("SELECT * FROM character_table WHERE character_id = :characterId")
    suspend fun getCharacter(characterId: Long): Character
}