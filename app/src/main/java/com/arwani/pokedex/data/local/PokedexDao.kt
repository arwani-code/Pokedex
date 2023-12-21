package com.arwani.pokedex.data.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexDao {

    @RawQuery(observedEntities = [PokedexEntity::class])
    fun getAllPokedex(query: SupportSQLiteQuery): DataSource.Factory<Int, PokedexEntity>

    @Query("SELECT * FROM pokedex WHERE name LIKE :value ORDER BY name ASC ")
    fun getSearchPokedex(value: String): Flow<List<PokedexEntity>>

    @Query("SELECT * FROM pokedex WHERE name LIKE :value ORDER BY name DESC ")
    fun getSearchPokedexDesc(value: String): Flow<List<PokedexEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokedex(pokedexEntity: List<PokedexEntity>)

    @Query("DELETE FROM pokedex")
    suspend fun delete()
}