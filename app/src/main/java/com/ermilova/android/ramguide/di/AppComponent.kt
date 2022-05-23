package com.ermilova.android.ramguide.di

import android.app.Application
import com.ermilova.android.character_details.CharacterDetailsViewModel
import com.ermilova.android.ramguide.CharactersListFragment
import com.ermilova.android.characters_list.CharactersListViewModel
import com.ermilova.android.core.di.CacheModule
import com.ermilova.android.core.di.CharacterModule
import com.ermilova.android.core.di.NetworkModule
import com.ermilova.android.ramguide.CharacterDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, CharacterModule::class, CacheModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun charactersListViewModel() : CharactersListViewModel
    fun characterDetailsViewModel(): CharacterDetailsViewModel

    fun inject(fragment: CharactersListFragment)
    fun inject(fragment: CharacterDetailsFragment)
}