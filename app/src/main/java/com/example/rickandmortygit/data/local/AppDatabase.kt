package com.example.rick_and_morty_vol2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun characterDao() : CharacterDao
}
