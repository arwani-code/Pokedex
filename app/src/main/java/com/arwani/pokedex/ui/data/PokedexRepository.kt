package com.arwani.pokedex.ui.data

import com.arwani.pokedex.ui.data.local.PokedexDao
import com.arwani.pokedex.ui.data.local.PokedexEntity
import com.arwani.pokedex.ui.data.network.ApiService
import com.arwani.pokedex.ui.data.network.PokedexResponse
import com.arwani.pokedex.ui.data.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val apiService: ApiService,
    private val pokedexDao: PokedexDao
) {

    fun getPokedexs(): Flow<PokedexResponse> = flow {
        emit(apiService.getPokedexs())
    }

    fun getDetailPokedex(id: Int): Flow<PokedexResponse> = flow {
        emit(apiService.getDetailPokedex(id))
    }

    suspend fun upsertData(result: List<Result>?) {
        val data = result?.map {
             PokedexEntity(
                id = it.url.toString(),
                name = it.name.toString()
            )
        }
        if (data != null) {
            pokedexDao.delete()
            pokedexDao.insertPokedex(data)
        }
    }

}