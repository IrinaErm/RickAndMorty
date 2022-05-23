package com.ermilova.android.characters_list

import com.ermilova.android.core.Character
import com.ermilova.android.core.CharacterRepo
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    suspend operator fun invoke(): List<Character> {
        return characterRepo.getAllCharacters()
    }
}