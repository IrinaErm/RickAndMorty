package com.ermilova.android.characters_list.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.usecase.GetAllCharactersUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.*

class CharactersListViewModel @Inject constructor(
    getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    val characters: Flow<PagingData<CharacterDomainModel>> =
        getAllCharactersUseCase().cachedIn(viewModelScope)
}