package com.radhangs.pokedexapp.pokedex

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.pokemondetail.PokemonDetailActivity
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.LoadingState
import com.radhangs.pokedexapp.shared.PokedexSprite
import com.radhangs.pokedexapp.shared.PokemonTitle
import com.radhangs.pokedexapp.shared.VerticalText
import com.radhangs.pokedexapp.shared.getDominantColor

@Composable
fun Pokedex(
    pokedexViewModel: PokedexViewModel = hiltViewModel()
) {
    pokedexViewModel.fetchData()
    val context = LocalContext.current
    val viewState = pokedexViewModel.viewState.observeAsState(
        initial = PokedexViewModel.PokedexViewState()
    )

    when (viewState.value.loadingState) {
        // we show a loading screen while waiting for the query to finish
        LoadingState.LOADING -> Loading()

        // if there are any errors, we can set this flag in the view model to display a
        // "something went wrong, try again" screen with a retry button
        LoadingState.ERROR -> ErrorTryAgain(pokedexViewModel::retry)

        LoadingState.INITIALIZED -> {
            val defaultGap = dimensionResource(id = R.dimen.default_gap)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(defaultGap),
                verticalArrangement = Arrangement.spacedBy(defaultGap)
            ) {
                items(viewState.value.pokedexList) { item ->
                    PokedexCard(item) {
                        pokedexViewModel.setSelectedPokemon(item.pokemonId)
                        context.startActivity(Intent(context, PokemonDetailActivity::class.java))
                    }
                }
            }
        }
        else -> { }
    }
}

@Composable
fun PokedexCard(item: PokedexPresentationModel, onPokemonClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPokemonClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.pokedex_number_width))
                .height(dimensionResource(id = R.dimen.sprite_size))
                .background(
                    getDominantColor(
                        LocalContext.current,
                        item.pokemonTypes.mainType.typeIconResourceId
                    )
                )
        ) {
            val pokedexNumberSidePadding = dimensionResource(
                id = R.dimen.pokedex_number_side_padding
            )
            val pokedexNumberString = item.pokemonId.toString()
            val pokedexNumberContentDescription = stringResource(
                id = R.string.pokedex_number_content_description
            )
            VerticalText(
                text = pokedexNumberString,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = pokedexNumberSidePadding, end = pokedexNumberSidePadding),
                style = TextStyle(fontSize = 16.sp, color = colorResource(id = R.color.white)),
                description = "$pokedexNumberContentDescription $pokedexNumberString"
            )
        }
        PokedexSprite(
            spriteUri = item.spriteUri,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.sprite_title_gap))
                .size(dimensionResource(id = R.dimen.sprite_size)),
            contentDescription = stringResource(id = R.string.pokemon_sprite_content_description)
        )
        PokemonTitle(
            pokemonName = item.pokemonName,
            pokemonTypes = item.pokemonTypes,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
