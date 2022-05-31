package com.ermilova.android.characters_list

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import com.ermilova.android.core.Character

class CharactersListViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private var _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllCharactersUseCase().let { list ->
                    withContext(Dispatchers.Main) {
                        _characters.value = list
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _characters.value = listOf()
                }
            }
        }
    }
}