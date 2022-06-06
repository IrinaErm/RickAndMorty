package com.ermilova.android.characters_list.presentation

import androidx.lifecycle.*
import com.ermilova.android.characters_list.domain.GetCharactersListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import com.ermilova.android.core.Character
import com.ermilova.android.core.utils.ApiStatus

class CharactersListViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase
) : ViewModel() {

    private var _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
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

                getCharactersListUseCase().let { charactersList ->
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