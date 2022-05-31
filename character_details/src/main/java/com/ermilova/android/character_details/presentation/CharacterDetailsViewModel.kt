package com.ermilova.android.character_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermilova.android.character_details.domain.GetCharacterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ermilova.android.core.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase): ViewModel() {
    private var _character = MutableLiveData<Character>()
    val character: LiveData<Character>
        get() = _character

    fun getCharacter(characterId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getCharacterUseCase(characterId).let { character ->
                withContext(Dispatchers.Main) {
                    _character.value = character
                }
            }
        }
    }
}