package com.ermilova.android.characters_list.presentation

import androidx.lifecycle.*
import com.ermilova.android.core.domain.model.CharacterDomainModel
import com.ermilova.android.core.domain.usecase.GetAllCharactersUseCase
import javax.inject.Inject
import com.ermilova.android.core.utils.ApiStatus
import kotlinx.coroutines.flow.*

class CharactersListViewModel @Inject constructor(
    getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    val characters: StateFlow<ApiStatus<List<CharacterDomainModel>>> = getAllCharactersUseCase()
        .stateIn(
            initialValue = ApiStatus.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
}