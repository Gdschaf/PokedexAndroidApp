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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.ViewModelProvider
import com.radhangs.pokedexapp.R
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
            // this first set of items is for all the stuff above the live of moves/header
            // needed a list of size one for this there's a static header which always
            // stays at the top but not header option that I could find.
            items(listOf("")) {
                LargePokemonImage(
                    pokemonDetailViewModel.getPokemonBitmap().value,
                    pokemonDetailViewModel.getPokemonDominantColor().value
                ) {
                    context.finish()
                }
                PokemonDetail(pokemonDetailViewModel.getPokemonDetails().value)
            }
            items(pokemonDetailViewModel.getPokemonMoves()) { item ->
                MoveCard(item)
            }
        }
    }
}

@Composable
fun PokemonDetail(details: PokemonDetailPresentationModel) {
    val defaultGap = dimensionResource(id = R.dimen.default_gap)
    Column(
        modifier = Modifier.fillMaxSize().padding(defaultGap),
        verticalArrangement = Arrangement.spacedBy(defaultGap),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonTitle(
            pokemonName = details.pokemonName,
            pokemonTypes = details.types,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .semantics(mergeDescendants = true) { }
        )
        PokemonCoreStats(details, Modifier.semantics(mergeDescendants = true) { })
        EvolutionChain(chain = details.evolutionChain)
        PokemonBaseStats(stats = details.baseStats)
    }
}

@Composable
fun BackIcon(
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.back_arrow_inverse),
            contentDescription = "Back",
            tint = LocalContentColor.current
        )
    }
}

@Composable
fun LargePokemonImage(bitmap: Bitmap?, backgroundColor: Color, onBackButtonPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        ImageFromBitmap(
            bitmap = bitmap,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.pokemon_detail_image_size))
                .padding(top = dimensionResource(id = R.dimen.default_gap)),
            contentDescription = stringResource(id = R.string.pokemon_image_content_description)
        )
        BackIcon(
            Modifier
                .align(Alignment.TopStart)
                .padding(dimensionResource(id = R.dimen.back_button_icon_padding))
        ) {
            onBackButtonPressed()
        }
    }
}