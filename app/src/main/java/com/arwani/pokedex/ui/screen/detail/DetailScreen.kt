package com.arwani.pokedex.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arwani.pokedex.R
import com.arwani.pokedex.ui.screen.common.UiState
import com.arwani.pokedex.ui.screen.components.PokedexTopAppBar

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {


    Scaffold(
        topBar = {
            PokedexTopAppBar(
                title = "Detail",
                canNavigate = true,
                navigateUp = { navController.navigateUp() })
        }
    ) { paddingValues ->
        viewModel.uiState.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    LaunchedEffect(key1 = Unit, block = {
                        viewModel.getDetail(id)
                    })
                    Column(
                        modifier = modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    LazyColumn(modifier = modifier.padding(paddingValues)){
                        val data = uiState.data.abilities
                        if (data?.isNotEmpty() == true){
                            item {
                                Column(
                                    modifier = modifier
                                        .padding(top = 24.dp)
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = uiState.data.name.toString(),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                            item {
                                Column(
                                    modifier = modifier
                                        .padding(top = 16.dp)
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(uiState.data.sprites?.backDefault.toString())
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = modifier.size(150.dp)
                                    )
                                }
                            }
                            item {
                                Column(
                                    modifier = modifier
                                        .padding(top = 24.dp, start = 12.dp)
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Abilities",
                                        textAlign = TextAlign.Center,
                                        fontSize = 32.sp,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            items(data){
                                Box(
                                    modifier = modifier
                                ) {
                                    Column(
                                        modifier = modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 16.dp
                                        ),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(text = "*  ${it.ability?.name.toString()}")
                                    }
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> {}
            }
        }
    }
}