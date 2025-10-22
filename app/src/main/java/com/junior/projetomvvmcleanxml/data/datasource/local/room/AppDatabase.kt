package com.junior.projetomvvmcleanxml.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.junior.projetomvvmcleanxml.data.model.item.ItemLocalEntity

@Database(
    entities = [ItemLocalEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}
