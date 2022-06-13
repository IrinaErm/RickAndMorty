package com.ermilova.android.core.domain.usecase

import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.utils.ApiStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    operator fun invoke(): Flow<ApiStatus<List<CharacterDomainModel>>> {
        return characterRepo.getAllCharacters()
    }
}