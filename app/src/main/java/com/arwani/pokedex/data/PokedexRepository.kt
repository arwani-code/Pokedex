package com.arwani.pokedex.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arwani.pokedex.data.local.PokedexDao
import com.arwani.pokedex.data.local.PokedexEntity
import com.arwani.pokedex.data.network.ApiService
import com.arwani.pokedex.data.network.PokedexResponse
import com.arwani.pokedex.data.network.Result
import com.arwani.pokedex.helper.SortType
import com.arwani.pokedex.helper.SortUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val apiService: ApiService,
    private val pokedexDao: PokedexDao
) {

    fun getAllPokedex(sortType: SortType): LiveData<PagedList<PokedexEntity>> {
        val query = SortUtils.getSortedQuery(sortType)
        val pokedex = pokedexDao.getAllPokedex(query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(pokedex, config).build()
    }

    fun getSearchPokedex(r: String): Flow<List<PokedexEntity>> = pokedexDao.getSearchPokedex(r)

    fun getSearchPokedexDesc(r: String): Flow<List<PokedexEntity>> =
        pokedexDao.getSearchPokedexDesc(r)

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