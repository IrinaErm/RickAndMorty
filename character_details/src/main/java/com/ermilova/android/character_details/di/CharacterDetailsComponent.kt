package com.ermilova.android.character_details.di

import com.ermilova.android.character_details.presentation.CharacterDetailsFragment
import dagger.Subcomponent

@Subcomponent
interface CharacterDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CharacterDetailsComponent
    }

    fun inject(characterDetailsFragment: CharacterDetailsFragment)
}