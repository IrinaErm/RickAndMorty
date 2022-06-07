package com.ermilova.android.character_details.domain

import com.ermilova.android.core.domain.CharacterModel
import com.ermilova.android.core.domain.CharacterRepo
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    suspend operator fun invoke(characterId: Long): CharacterModel {
        return characterRepo.getCharacter(characterId)
    }
}