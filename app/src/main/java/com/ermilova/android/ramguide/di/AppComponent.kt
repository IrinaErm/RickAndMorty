package com.ermilova.android.ramguide.di

import android.app.Application
import com.ermilova.android.character_details.di.CharacterDetailsComponent
import com.ermilova.android.characters_list.di.CharactersListComponent
import com.ermilova.android.core.di.CacheModule
import com.ermilova.android.core.di.CharacterModule
import com.ermilova.android.core.di.NetworkModule
import com.ermilova.android.core.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, CharacterModule::class, CacheModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun charactersListComponent(): CharactersListComponent.Factory
    fun characterDetailsComponent(): CharacterDetailsComponent.Factory
}