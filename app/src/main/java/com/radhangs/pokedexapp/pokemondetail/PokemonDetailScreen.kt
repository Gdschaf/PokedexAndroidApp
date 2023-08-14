package com.radhangs.pokedexapp.pokemondetail

import android.app.Activity
import android.graphics.Bitmap
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.pokedex.PokedexViewModel
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromBitmap
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.PokemonTitle

@Composable
fun PokemonDetailScreen(
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val viewState = pokemonDetailViewModel.viewState.observeAsState(initial = PokemonDetailViewModel.PokemonDetailViewState())

    if(viewState.value.loading) {
        Loading()
    }
    else if(viewState.value.error) {
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
                    viewState.value.largeImageBitmap,
                    viewState.value.largeImageDominantColor
                ) {
                    context.finish()
                }
                PokemonDetail(viewState.value.pokemonDetail)
            }
            items(viewState.value.pokemonMoves) { item ->
                MoveCard(item)
            }
        }
    }
}

@Composable
fun PokemonDetail(details: PokemonDetailPresentationModel) {
    val defaultGap = dimensionResource(id = R.dimen.default_gap)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultGap),
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