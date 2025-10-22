package com.junior.projetomvvmcleanxml.di

import com.junior.projetomvvmcleanxml.data.datasource.local.sharedpreference.SyncPreference
import com.junior.projetomvvmcleanxml.data.datasource.local.sharedpreference.UsersPreference
import com.junior.projetomvvmcleanxml.data.repository.ItemRepositoryImpl
import com.junior.projetomvvmcleanxml.data.repository.UserRepositoryImpl
import com.junior.projetomvvmcleanxml.data.repository.authrepository.AuthRepositoryImpl
import com.junior.projetomvvmcleanxml.domain.repository.AuthRepository
import com.junior.projetomvvmcleanxml.domain.repository.ItemRepository
import com.junior.projetomvvmcleanxml.domain.repository.SyncPreferenceRepository
import com.junior.projetomvvmcleanxml.domain.repository.UserRepository
import com.junior.projetomvvmcleanxml.domain.repository.UserSessionDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindUserSessionDataSource( impl: UsersPreference): UserSessionDataSource

    @Binds
    @Singleton
    abstract fun bindSyncPreferenceRepository(impl: SyncPreference): SyncPreferenceRepository

}