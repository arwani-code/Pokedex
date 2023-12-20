package com.arwani.pokedex.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arwani.pokedex.ui.screen.detail.DetailScreen
import com.arwani.pokedex.ui.screen.home.MainScreen

@Composable
fun PokedexNavHost(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            MainScreen(navController = navController) {
                navController.navigate(Screen.Detail.createRoute(it))
            }
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("pokedexId") { type = NavType.IntType }),
        ) {
            val id = it.arguments?.getInt("pokedexId") ?: 1
            DetailScreen(id = id, navController = navController)
        }

    }
}