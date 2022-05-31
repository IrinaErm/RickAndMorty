package com.ermilova.android.characters_list.di

import com.ermilova.android.characters_list.presentation.CharactersListFragment
import dagger.Subcomponent

@Subcomponent
interface CharactersListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CharactersListComponent
    }

    fun inject(charactersListFragment: CharactersListFragment)
}