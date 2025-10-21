package com.junior.projetomvvmcleanxml.data.datasource.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.junior.projetomvvmcleanxml.data.model.item.ItemLocalEntity

@Database(entities = [ItemLocalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
