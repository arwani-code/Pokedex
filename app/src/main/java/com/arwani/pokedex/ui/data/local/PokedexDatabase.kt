package com.arwani.pokedex.ui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokedexEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokedexDao(): PokedexDao
}