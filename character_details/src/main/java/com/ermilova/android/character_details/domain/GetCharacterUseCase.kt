package com.ermilova.android.character_details.domain

import com.ermilova.android.core.Character
import com.ermilova.android.core.CharacterRepo
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    suspend operator fun invoke(characterId: Long): Character {
        return characterRepo.getCharacter(characterId)
    }
}