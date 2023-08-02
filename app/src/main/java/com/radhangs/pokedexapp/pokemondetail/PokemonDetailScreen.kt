package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationData
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromUrl
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.PokemonTitle
import com.radhangs.pokedexapp.shared.apolloClient

@Composable
fun PokemonDetailScreen(context: ViewModelStoreOwner, pokemonId: Int) {
    val pokemonDetailViewModel = ViewModelProvider(context, PokemonDetailViewModelFactory(
        apolloClient(),
        pokemonId
    )
    ).get(PokemonDetailViewModel::class.java)

    if(pokemonDetailViewModel.isLoading().value) {
        Loading()
    }
    else if(pokemonDetailViewModel.hasError().value) {
        ErrorTryAgain(pokemonDetailViewModel::retry)
    }
    else {
        LazyColumn(
            // modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // todo add a back button that isn't on the tool back, maybe a small circle back icon over the image
            // this first set of items is for all the stuff above the live of moves/header
            items(listOf("")) {
                LargePokemonImage(pokemonId)
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
                .padding(start = 16.dp, end = 16.dp)
        )
        PokemonStats(details, Modifier.padding(16.dp))
    }
}

@Composable
fun LargePokemonImage(pokemonId: Int) {
    ImageFromUrl(
        url = Constants.LARGE_POKEMON_IMAGE_URL + getFormattedImageFilename(pokemonId),
        modifier = Modifier
            .size(300.dp)
            .padding(32.dp),
        contentDescription = "Pokemon Image"
    )
}

fun getFormattedImageFilename(pokemonId: Int): String = String.format("%03d.png", pokemonId)