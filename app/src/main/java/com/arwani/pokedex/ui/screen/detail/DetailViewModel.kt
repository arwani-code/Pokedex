package com.arwani.pokedex.ui.screen.detail

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
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PokedexRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<PokedexResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<PokedexResponse>> get() = _uiState

    fun getDetail(id: Int) {
        viewModelScope.launch {
            repository.getDetailPokedex(id)
                .flowOn(Dispatchers.IO)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }
}