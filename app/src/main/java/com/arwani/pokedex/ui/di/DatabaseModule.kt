package com.arwani.pokedex.ui.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.arwani.pokedex.ui.data.local.PokedexDao
import com.arwani.pokedex.ui.data.local.PokedexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PokedexDatabase {

        return Room.databaseBuilder(
            context,
            PokedexDatabase::class.java,
            "Pokedex.Db",
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideColPickingOpen(database: PokedexDatabase): PokedexDao =
        database.pokedexDao()
}