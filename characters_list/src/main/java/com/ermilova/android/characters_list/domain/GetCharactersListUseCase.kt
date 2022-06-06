package com.ermilova.android.characters_list.domain

import com.ermilova.android.core.Character
import com.ermilova.android.core.CharacterRepo
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    suspend operator fun invoke(): List<Character> {
        return characterRepo.getAllCharacters()
    }
}