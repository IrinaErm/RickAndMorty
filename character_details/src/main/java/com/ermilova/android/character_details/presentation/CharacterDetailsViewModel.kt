package com.ermilova.android.character_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermilova.android.character_details.domain.GetCharacterUseCase
import com.ermilova.android.core.domain.CharacterModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase) :
    ViewModel() {

    private var _character = MutableLiveData<CharacterModel>()
    val character: LiveData<CharacterModel>
        get() = _character

    fun getCharacter(characterId: Long) {
        viewModelScope.launch {
            getCharacterUseCase(characterId).let { character ->
                _character.value = character
            }
        }
    }
}