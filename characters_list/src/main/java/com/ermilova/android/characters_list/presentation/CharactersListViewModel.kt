package com.ermilova.android.characters_list.presentation

import androidx.lifecycle.*
import com.ermilova.android.core.domain.usecase.GetAllCharactersUseCase
import javax.inject.Inject
import com.ermilova.android.core.utils.ApiStatus
import kotlinx.coroutines.flow.*
import java.lang.Exception

class CharactersListViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    val characters: StateFlow<ApiStatus> = flow {
        try {
            emit(ApiStatus.Loaded(getAllCharactersUseCase()))
        } catch(e: Exception) {
            emit(ApiStatus.Error(e))
        }
    }.stateIn(
        initialValue = ApiStatus.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )
}