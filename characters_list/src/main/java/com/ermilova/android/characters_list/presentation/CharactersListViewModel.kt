package com.ermilova.android.characters_list.presentation

import androidx.lifecycle.*
import com.ermilova.android.characters_list.domain.GetAllCharactersUseCase
import com.ermilova.android.core.domain.CharacterModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import com.ermilova.android.core.utils.ApiStatus

class CharactersListViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private var _characters = MutableLiveData<List<CharacterModel>>()
    val characters: LiveData<List<CharacterModel>>
        get() = _characters

    private val _loadingStatus = MutableLiveData<ApiStatus>()
    val loadingStatus: LiveData<ApiStatus>
        get() = _loadingStatus

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                _loadingStatus.value = ApiStatus.LOADING

                getAllCharactersUseCase().let { charactersList ->
                    _characters.value = charactersList
                }

                _loadingStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _loadingStatus.value = ApiStatus.ERROR
            }
        }
    }
}