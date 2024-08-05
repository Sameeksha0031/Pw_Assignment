package com.example.physics_wallah_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.physics_wallah_assignment.apiCalling.PokemonViewModel
import com.example.physics_wallah_assignment.ui.theme.Physics_Wallah_AssignmentTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Physics_Wallah_AssignmentTheme {
                val pokemonViewModel = viewModel(PokemonViewModel::class.java)
                val navController = rememberNavController()
                pokemonViewModel.getPokemonList(0,20)
                ComposeNavigation(navController = navController,pokemonViewModel)
            }
        }
    }
}
