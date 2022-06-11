package com.ermilova.android.core.di

import android.app.Application
import com.ermilova.android.core.data.room.AppDatabase
import com.ermilova.android.core.data.room.CharacterDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun provideRoomInstance(application: Application): AppDatabase {
        return AppDatabase.getInstance(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.characterDao()
    }
}