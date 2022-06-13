package com.ermilova.android.core.domain.usecase

import androidx.paging.PagingData
import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.domain.model.CharacterDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val characterRepo: CharacterRepo) {
    operator fun invoke(): Flow<PagingData<CharacterDomainModel>> {
        return characterRepo.getAllCharacters()
    }
}