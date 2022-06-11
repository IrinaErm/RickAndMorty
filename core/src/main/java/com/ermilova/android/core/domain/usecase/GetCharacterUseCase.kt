package com.ermilova.android.core.domain.usecase

import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.CharacterRepo
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    suspend operator fun invoke(characterId: Long): CharacterDomainModel {
        return characterRepo.getCharacter(characterId)
    }
}