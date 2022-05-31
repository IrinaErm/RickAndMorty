package com.ermilova.android.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ermilova.android.core.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory<ViewModel>): ViewModelProvider.Factory
}