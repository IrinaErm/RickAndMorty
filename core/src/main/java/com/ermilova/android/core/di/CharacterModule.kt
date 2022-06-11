package com.ermilova.android.core.di

import com.ermilova.android.core.domain.CharacterRepo
import com.ermilova.android.core.data.CharacterRepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CharacterModule {
    @Binds
    abstract fun provideCharacterRepo(repo: CharacterRepoImpl): CharacterRepo
}