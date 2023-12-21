package com.arwani.pokedex.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.arwani.pokedex.data.PokedexRepository
import com.arwani.pokedex.data.local.PokedexEntity
import com.arwani.pokedex.data.network.PokedexResponse
import com.arwani.pokedex.helper.SortType
import com.arwani.pokedex.ui.screen.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainVieModel @Inject constructor(
    private val repository: PokedexRepository
) : ViewModel() {

    private val _sort = MutableLiveData<SortType>()

    var inputQuery by mutableStateOf("")
    var option by mutableStateOf("ASC")

    private val _uiState: MutableStateFlow<UiState<PokedexResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<PokedexResponse>> get() = _uiState

    private val dataItems = mutableStateListOf<PokedexResponse>()

    init {
        initPokedex()
        _sort.value = SortType.ASCENDING
    }

    fun getSearchPokedex(): Flow<List<PokedexEntity>> = when (option) {
        "ASC" -> {
            repository.getSearchPokedex("%$inputQuery%")
        }
        else -> {
            repository.getSearchPokedexDesc("%$inputQuery%")
        }
    }

    private fun initPokedex() {
        viewModelScope.launch {
            repository.getPokedexs()
                .flowOn(Dispatchers.IO)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect {
                    repository.upsertData(it.results)
                    dataItems.add(it)
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}
