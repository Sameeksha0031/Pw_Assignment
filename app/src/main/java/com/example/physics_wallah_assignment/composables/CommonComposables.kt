package com.example.physics_wallah_assignment.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.physics_wallah_assignment.R
import com.example.physics_wallah_assignment.apiCalling.PokemonViewModel
import com.example.physics_wallah_assignment.apiCalling.Result
import com.example.physics_wallah_assignment.ui.theme.Background
import com.example.physics_wallah_assignment.ui.theme.CardColor
import com.example.physics_wallah_assignment.ui.theme.TextColor

@Composable
fun PokemonListScreen(navController: NavHostController, pokemonViewModel: PokemonViewModel) {

    val pokemonList by pokemonViewModel.pokemonList.collectAsState()
    val lazyListState = rememberLazyListState()
    var offset by remember { mutableIntStateOf(20) }
    var limit by remember { mutableIntStateOf(20) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Background)
        .navigationBarsPadding()
    ) {
        SideEffect()
        Column {
            Text(
                modifier = Modifier.padding(start = 15.dp, top = 30.dp,25.dp),
                text = "Pokemon List", color = TextColor.copy(alpha = 0.5F),
                fontSize = 30.sp, fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold
            )

            if(pokemonList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(
                            align = Alignment.Center
                        )
                )
            } else {
                val currentIndex = remember {
                    derivedStateOf {
                        val visibleItemsInfo = lazyListState.layoutInfo.visibleItemsInfo
                        if (visibleItemsInfo.isNotEmpty()) {
                            val lastVisibleItemIndex = visibleItemsInfo.last().index
                            val totalItemsCount = lazyListState.layoutInfo.totalItemsCount
                            lastVisibleItemIndex >= totalItemsCount - 5
                        }else{
                            false
                        }
                    }
                }
                if(currentIndex.value && lazyListState.canScrollForward && lazyListState.canScrollBackward) {
                    pokemonViewModel.getPokemonList(offset,limit)
                    offset += 20
                }
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    state = lazyListState
                ) {
                    items(pokemonList.size){ character ->
                        pokemonList[character]?.let { CardViewForList(it,navController,pokemonViewModel) }
                    }
                }
            }
        }
    }
}


@Composable
fun CardViewForList(
    character: Result,
    navController: NavHostController,
    pokemonViewModel: PokemonViewModel
) {
    Spacer(modifier = Modifier.padding(4.dp))
    Card(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(18.dp))
            .clickable {
                pokemonViewModel.getPokemonDetail(character.name)
                navController.navigate("PokemonDetail")
            },
        colors = CardColors(
            contentColor = CardColor,
            containerColor = CardColor,
            disabledContentColor = CardColor,
            disabledContainerColor = CardColor
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = Modifier.padding(end = 8.dp), painter = painterResource(id = R.drawable.pokemon_ball), contentDescription = "Icon")
            Text(
                modifier = Modifier,
                text = character.name.capitalFirstLetter(character.name), color = TextColor.copy(alpha = 0.5F),
                fontSize = 20.sp, fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

fun String.capitalFirstLetter(string : String) : String{
    return string.first().uppercase()+string.drop(1)
}




