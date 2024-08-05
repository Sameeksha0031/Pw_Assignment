package com.example.physics_wallah_assignment.composables

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.physics_wallah_assignment.apiCalling.Move
import com.example.physics_wallah_assignment.apiCalling.PokemonViewModel
import com.example.physics_wallah_assignment.apiCalling.Type
import com.example.physics_wallah_assignment.apiCalling.VersionGroupDetail
import com.example.physics_wallah_assignment.ui.theme.Background
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.rememberDrawablePainter

@Composable
fun PokemonDetail(navController: NavHostController, pokemonViewModel: PokemonViewModel) {

    val pokemonDetail by pokemonViewModel.pokemonDetail.collectAsState()
    DisposableEffect(key1 = Unit) {
        onDispose {
            pokemonViewModel.removePokemonDetail()
        }
    }
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    var isSelected by remember {
        mutableStateOf(true)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()){
        SideEffect()
        Card(modifier = Modifier
            .fillMaxSize()
            .background(Background),
            elevation = 20.dp) {
            if(pokemonDetail == null) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(
                            align = Alignment.Center
                        )
                )
            } else {
                LazyColumn(Modifier.background(Background)) {

                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            pokemonDetail?.sprites?.other?.home?.frontDefault?.let {
                                CardViewForImage(
                                    it
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                ),
                                text = pokemonDetail?.name.toString()
                                    .capitalFirstLetter(pokemonDetail?.name.toString()),
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier) {
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 15.dp,
                                            top = 3.dp,
                                            bottom = 3.dp
                                        ),
                                        text = "Height :", color = Color.Black,
                                        fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 8.dp,
                                            top = 3.dp,
                                            bottom = 3.dp
                                        ),
                                        text = pokemonDetail?.height.toString(),
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.ExtraBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Row(modifier = Modifier) {
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 15.dp,
                                            top = 3.dp,
                                            bottom = 3.dp
                                        ),
                                        text = "Weight :", color = Color.Black,
                                        fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 8.dp,
                                            top = 3.dp,
                                            bottom = 3.dp,
                                            end = 15.dp
                                        ),
                                        text = pokemonDetail?.weight.toString(),
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.ExtraBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            var typeString = ""
                            pokemonDetail?.types?.forEachIndexed { index, value ->
                                typeString =
                                    if ((pokemonDetail?.types?.size?.minus(1)) == index) "$typeString${
                                        value.type.name.capitalFirstLetter(value.type.name)
                                    } "
                                    else "$typeString${value.type.name.capitalFirstLetter(value.type.name)}, "
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .border(
                                        0.5.dp, color = Color.White,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .padding(6.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 4.dp,
                                            top = 8.dp,
                                            bottom = 3.dp,
                                            end = 15.dp
                                        ),
                                    text = "Type", color = Color.Black,
                                    fontSize = 18.sp, fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.ExtraBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 4.dp,
                                            top = 35.dp,
                                            bottom = 3.dp,
                                            end = 15.dp
                                        ),
                                    text = typeString, color = Color.Black,
                                    fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.ExtraBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .border(
                                        0.5.dp, color = Color.White,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .padding(6.dp, bottom = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 6.dp,
                                        end = 6.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            start = 0.dp,
                                            top = 8.dp,
                                            bottom = 8.dp
                                        ),
                                        text = "Moves", color = Color.Black,
                                        fontSize = 18.sp, fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    LazyRow {
                                        pokemonDetail?.moves?.size?.let {
                                            items(it) { index: Int ->
                                                CardViewForHorizontalView(
                                                    pokemonDetail!!.moves[index],
                                                    index,
                                                    onSelected = { index, selected ->
                                                        selectedIndex = index
                                                        isSelected = selected
                                                    },
                                                    selected = selectedIndex
                                                )

                                            }
                                        }
                                    }
                                    if (isSelected) {
                                        pokemonDetail?.moves?.get(selectedIndex)
                                            ?.let { MovesDetails(it.versionGroupDetails) }
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                                    .border(
                                        0.5.dp, color = Color.White,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .padding(6.dp, bottom = 8.dp)
                            ) {
                                Column {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 4.dp,
                                                top = 8.dp,
                                                end = 15.dp
                                            ),
                                        text = "Abilities", color = Color.Black,
                                        fontSize = 18.sp, fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.ExtraBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    pokemonDetail?.abilities?.forEachIndexed { index, ability ->
                                        TitleAndSubtext(
                                            title = "Slot -",
                                            value = ability.slot.toString(),
                                            Modifier.padding(start = 3.dp, end = 8.dp)
                                        )
                                        TitleAndSubtext(
                                            title = "Ability Name -",
                                            value = ability.ability.name,
                                            Modifier.padding(start = 2.dp, end = 8.dp)
                                        )
                                        TitleAndSubtext(
                                            title = "Is Hidden -",
                                            value = ability.isHidden.toString(),
                                            Modifier.padding(start = 2.dp, end = 8.dp)
                                        )
                                        if((pokemonDetail?.abilities?.size?.minus(1)) != index) {
                                            Divider(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(0.5.dp)
                                                    .padding(start = 3.dp, end = 5.dp),
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CardViewForHorizontalView(
    move: Move,
    index: Int,
    onSelected: (index: Int,selected : Boolean) -> Unit,
    selected: Int
) {
    Spacer(modifier = Modifier.width(5.dp))
    Box(modifier = Modifier
        .wrapContentHeight()
        .background(color = Color.Green)
        .border(width = 3.dp, color = if (selected == index) Color.Black else Color.White),
        contentAlignment = Alignment.Center){
        Card(modifier = Modifier
            .clickable {
                onSelected(index, true)
            }
            .background(color = Color.Yellow)
            .border(width = 3.dp, color = if (selected == index) Color.Black else Color.White),
            elevation = 8.dp){
            Text(
                modifier = Modifier.padding(start = 15.dp, top = 8.dp, bottom = 8.dp, end = 15.dp),
                text = move.move.name, color = Color.Black,
                fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun CardViewForImage(image: String) {
    var imageDrawable by remember { mutableStateOf<Drawable?>(null) }
    Glide.with(LocalContext.current).load(image)
        .apply(RequestOptions().centerCrop())
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                imageDrawable = resource
            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    Log.d("ApiCalling", "CardViewForImage: image $image")
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(shape = RoundedCornerShape(18.dp), color = Color.White),
        contentAlignment = Alignment.Center){
        Card(modifier = Modifier.width(screenWidth),
            elevation = 20.dp){
            Image(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(end = 15.dp), painter = rememberDrawablePainter(drawable = imageDrawable), contentDescription = "")
        }
    }
}

@Composable
fun TitleAndSubtext(title : String, value : String,modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier,
            text = title, color = Color.Black,
            fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            modifier = modifier,
            text = value, color = Color.Black,
            fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SideEffect() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    systemUiController.setStatusBarColor(
        color = Background,
        darkIcons = useDarkIcons
    )

    // Set navigation bar color
    systemUiController.setNavigationBarColor(
        color = Background,
        darkIcons = useDarkIcons
    )
}


@Composable
fun MovesDetails(versionGroupDetails: List<VersionGroupDetail>) {
    Box {
        Column {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 3.dp),
                text = "Version Group Detail", color = Color.Black,
                fontSize = 15.sp, fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold
            )

            versionGroupDetails.forEachIndexed{ index, value ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 6.dp, top = 6.dp)
                    .border(
                        0.5.dp, color = Color.White,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(6.dp, bottom = 8.dp)
                ) {
                    TitleAndSubtext(title = "Move Learn Method -", value = value.moveLearnMethod.name,Modifier.padding(start = 2.dp, top = 5.dp, end = 8.dp))
                    TitleAndSubtext(title = "Version Group -", value = value.versionGroup.name,Modifier.padding(start = 2.dp, top = 18.dp,end = 8.dp))
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth())
                }
            }
        }
    }
}
