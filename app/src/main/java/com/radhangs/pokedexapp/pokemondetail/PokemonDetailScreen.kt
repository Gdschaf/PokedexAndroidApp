package com.radhangs.pokedexapp.pokemondetail

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromBitmap
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.PokemonTitle
import com.radhangs.pokedexapp.shared.apolloClient

@Composable
fun PokemonDetailScreen(context: ComponentActivity, pokemonId: Int) {
    val pokemonDetailViewModel = ViewModelProvider(context, PokemonDetailViewModelFactory(
        apolloClient(),
        pokemonId
    ))[PokemonDetailViewModel::class.java]

    if(pokemonDetailViewModel.isLoading().value) {
        Loading()
    }
    else if(pokemonDetailViewModel.hasError().value) {
        ErrorTryAgain(pokemonDetailViewModel::retry)
    }
    else {
        pokemonDetailViewModel.loadLargeBitmap(context)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // todo add a back button that isn't on the tool back, maybe a small circle back icon over the image
            // this first set of items is for all the stuff above the live of moves/header
            items(listOf("")) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = pokemonDetailViewModel.getPokemonDominantColor().value),
                    contentAlignment = Alignment.Center
                ) {
                    LargePokemonImage(pokemonDetailViewModel.getPokemonBitmap().value)
                }
                PokemonDetail(pokemonId, pokemonDetailViewModel.getPokemonDetails().value)
                MoveHeader()
            }
            items(pokemonDetailViewModel.getPokemonMoves()) { item ->
                Move(item)
            }
        }
    }
}

@Composable
fun PokemonDetail(pokemonId: Int, details: PokemonDetailPresentationModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonTitle(
            pokemonName = details.pokemonName,
            pokemonTypes = details.types,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        PokemonStats(details, Modifier.padding(16.dp))
    }
}

@Composable
fun LargePokemonImage(bitmap: Bitmap?) {
    ImageFromBitmap(
        bitmap = bitmap,
        modifier = Modifier
            .size(300.dp)
            .padding(top = 16.dp),
        contentDescription = "Pokemon Image"
    )
}

fun getFormattedImageFilename(pokemonId: Int): String = String.format("%03d.png", pokemonId)