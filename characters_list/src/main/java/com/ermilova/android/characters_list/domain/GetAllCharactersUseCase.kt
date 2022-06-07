package com.ermilova.android.characters_list.domain

import com.ermilova.android.core.domain.CharacterModel
import com.ermilova.android.core.domain.CharacterRepo
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    suspend operator fun invoke(): List<CharacterModel> {
        return characterRepo.getAllCharacters()
    }
}