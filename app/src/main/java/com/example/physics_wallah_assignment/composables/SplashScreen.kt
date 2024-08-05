package com.example.physics_wallah_assignment.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.physics_wallah_assignment.R

@Composable
fun SplashScreen () {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Image(modifier = Modifier.size(100.dp,100.dp), painter = painterResource(id = R.drawable.pokemon_ball), contentDescription = "splash_icon")
    }
}