package com.arwani.pokedex.ui.screen.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arwani.pokedex.ui.data.PokedexRepository
import com.arwani.pokedex.ui.data.network.PokedexResponse
import com.arwani.pokedex.ui.screen.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _uiState: MutableStateFlow<UiState<PokedexResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<PokedexResponse>> get() = _uiState

    private val dataItems = mutableStateListOf<PokedexResponse>()

    init {
        initPokedex()
    }


    fun search(r: String){
        viewModelScope.launch {
            val data = withContext(Dispatchers.Default){
                dataItems.filter { item -> item.name.toString().contains(r) }
            }
            if (data.isNotEmpty() && data != null){
                _uiState.value = UiState.Success(data.first())
            }
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

data class NameItem(val name: String)