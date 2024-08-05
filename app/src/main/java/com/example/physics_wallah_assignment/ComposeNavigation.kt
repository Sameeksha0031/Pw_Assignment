package com.example.physics_wallah_assignment

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.physics_wallah_assignment.apiCalling.PokemonViewModel
import com.example.physics_wallah_assignment.composables.PokemonDetail
import com.example.physics_wallah_assignment.composables.PokemonListScreen
import com.example.physics_wallah_assignment.composables.SplashScreen

@Composable
fun ComposeNavigation(navController: NavHostController, pokemonViewModel: PokemonViewModel) {
    NavHost(navController = navController, startDestination = "PokemonListScreen") {

        composable("PokemonListScreen") {
            PokemonListScreen(navController,pokemonViewModel)
        }

        composable("PokemonDetail/{characterName}",
            arguments = listOf(navArgument("characterName"){
                type = NavType.StringType
            })
        ){ it ->
            it.arguments?.getString("characterName")
                ?.let { it1 -> PokemonDetail(pokemonViewModel, characterName = it1) }
        }

        composable("SplashScreen"){
            SplashScreen()
        }
    }
}