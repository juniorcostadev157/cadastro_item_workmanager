package com.junior.projetomvvmcleanxml.di


import android.content.Context
import androidx.room.Room
import com.junior.projetomvvmcleanxml.data.datasource.local.room.AppDatabase
import com.junior.projetomvvmcleanxml.data.datasource.local.room.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase( @ApplicationContext appContext: Context): AppDatabase{
      return  Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
             "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase): ItemDao{
        return database.itemDao()
    }
}