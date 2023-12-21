package com.arwani.pokedex.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arwani.pokedex.ui.screen.common.UiState
import com.arwani.pokedex.ui.screen.components.DropDownMenuItems
import com.arwani.pokedex.ui.screen.components.PokedexTopAppBar
import com.arwani.pokedex.ui.screen.components.TopAppSearchBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    vieModel: MainVieModel = hiltViewModel(),
    job: CoroutineScope = rememberCoroutineScope(),
    navigateToDetail: (Int) -> Unit
) {

    var topState by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            when (topState) {
                true -> {
                    TopAppSearchBar(
                        navigateUp = { topState = false },
                        onChangeValue = {
                            vieModel.inputQuery = it
                        }
                    )
                }

                else -> {
                    PokedexTopAppBar(
                        title = "Home",
                        actionsRow = {
                            IconButton(onClick = { topState = true }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Black.copy(0.7f)
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                DropDownMenuItems(isUpdateItem = {
                                    vieModel.option = it
                                })
                            }
                        }

                    )
                }
            }
        },
    ) { paddingValues ->
        vieModel.uiState.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {}

                is UiState.Error -> {}
            }
            vieModel.getSearchPokedex().collectAsState(initial = emptyList()).value.let { items ->
                LazyColumn(
                    modifier = modifier.padding(paddingValues)
                ) {
                    itemsIndexed(items) { _, data ->
                        Box(
                            modifier = modifier.clickable {
                                vieModel.inputQuery = ""
                                navigateToDetail(data.pokdexId.plus(1))
                            }
                        ) {
                            Column(
                                modifier = modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 16.dp
                                ),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(text = data.name)
                                Divider(thickness = 2.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}