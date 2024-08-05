package com.example.physics_wallah_assignment

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.physics_wallah_assignment.apiCalling.PokemonViewModel
import com.example.physics_wallah_assignment.composables.SplashScreen
import com.example.physics_wallah_assignment.ui.theme.Physics_Wallah_AssignmentTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showScreen by remember { mutableStateOf(false) }
            Physics_Wallah_AssignmentTheme {
                val pokemonViewModel = viewModel(PokemonViewModel::class.java)
                val navController = rememberNavController()
                pokemonViewModel.getPokemonList(0,20)
                Handler().postDelayed({
                    showScreen = true
                },2000)
               if (showScreen){
                   ComposeNavigation(navController = navController,pokemonViewModel)
               } else {
                   SplashScreen()
               }
            }
        }
    }
}
