package com.ermilova.android.ramguide

import android.app.Application
import com.ermilova.android.character_details.di.CharacterDetailsComponent
import com.ermilova.android.character_details.di.CharacterDetailsComponentProvider
import com.ermilova.android.characters_list.di.CharactersListComponent
import com.ermilova.android.characters_list.di.CharactersListComponentProvider
import com.ermilova.android.ramguide.di.AppComponent
import com.ermilova.android.ramguide.di.DaggerAppComponent

class MyApplication : Application(), CharactersListComponentProvider, CharacterDetailsComponentProvider {
    val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()

    override fun provideCharactersListComponent(): CharactersListComponent {
        return appComponent.charactersListComponent().create()
    }

    override fun provideCharacterDetailsComponent(): CharacterDetailsComponent {
        return appComponent.characterDetailsComponent().create()
    }
}