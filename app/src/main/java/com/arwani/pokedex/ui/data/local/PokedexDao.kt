package com.arwani.pokedex.ui.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexDao {

    @Query("SELECT * FROM pokedex WHERE name LIKE :value")
    fun getSearchPokedex(value: String): Flow<List<PokedexEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokedex(pokedexEntity: List<PokedexEntity>)

    @Query("DELETE FROM pokedex")
    suspend fun delete()
}