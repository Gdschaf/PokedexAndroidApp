package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationData
import com.radhangs.pokedexapp.pokedex.PokemonTypes
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromUrl
import com.radhangs.pokedexapp.shared.Loading
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
        Column {
            ToolbarWithBackButton("Back", { })
            PokemonDetail(pokemonId, pokemonDetailViewModel.getPokemonDetails().value)
            ListOfMoves(moves = pokemonDetailViewModel.getPokemonMoves())
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
        LargePokemonImage(pokemonId)
        DetailHeading(details)
        DetailStats(details)
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

@Composable
fun DetailHeading(details: PokemonDetailPresentationModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = details.pokemonName)
        PokemonTypes(details.types)
    }
}

@Composable
fun DetailStats(details: PokemonDetailPresentationModel) {

}

@Composable
fun ListOfMoves(moves: List<PokemonMovePresentationData>) {

}

fun getFormattedImageFilename(pokemonId: Int): String = String.format("%03d.png", pokemonId)


// temporary, should go buhbye
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithBackButton(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
                Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back", // TODO create a string resource
                    tint = Color.White // Customize the back button color if needed
                )
            }
        },
        actions = {
            // Add additional actions here if needed
        }
    )
}