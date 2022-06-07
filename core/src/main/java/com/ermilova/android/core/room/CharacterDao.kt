package com.ermilova.android.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(vararg character: CharacterEntity)

    @Query("SELECT * FROM character_table WHERE character_id = :characterId")
    suspend fun getCharacter(characterId: Long): CharacterEntity
}