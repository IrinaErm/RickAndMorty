package com.ermilova.android.character_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermilova.android.core.domain.usecase.GetCharacterUseCase
import com.ermilova.android.core.domain.model.CharacterDomainModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase) :
    ViewModel() {

    private var _character = MutableLiveData<CharacterDomainModel>()
    val character: LiveData<CharacterDomainModel>
        get() = _character

    fun getCharacter(characterId: Long) {
        viewModelScope.launch {
            getCharacterUseCase(characterId).let { character ->
                _character.value = character
            }
        }
    }
}