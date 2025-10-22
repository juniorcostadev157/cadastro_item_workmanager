package com.junior.projetomvvmcleanxml.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Qualifier
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher


@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {


    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher{
        return Dispatchers.Default
    }

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher{
        return Dispatchers.Main
    }




}