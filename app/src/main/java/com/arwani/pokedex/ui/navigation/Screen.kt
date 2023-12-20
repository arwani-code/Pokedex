package com.arwani.pokedex.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("home/{pokedexId}") {
        fun createRoute(pokedexId: Int) = "home/$pokedexId"
    }
}