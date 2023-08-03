package com.radhangs.pokedexapp.pokedex

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.getDrawableTypeIcon
import com.radhangs.pokedexapp.pokemondetail.PokemonDetailIntent
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.PokedexSprite
import com.radhangs.pokedexapp.shared.PokemonTitle
import com.radhangs.pokedexapp.shared.VerticalText
import com.radhangs.pokedexapp.shared.apolloClient
import com.radhangs.pokedexapp.shared.getDominantColor

@Composable
fun Pokedex(context: ComponentActivity) {
    val pokedexViewModel = ViewModelProvider(
        context,
        PokedexViewModelFactory(apolloClient())
    )[PokedexViewModel::class.java]

    // we show a loading screen while waiting for the query to finish
    if(pokedexViewModel.isLoading().value) {
        Loading()
    }
    // if there are any errors, we can set this flag in the view model to display a
    // "something went wrong, try again" screen with a retry button
    else if(pokedexViewModel.hasError().value) {
        ErrorTryAgain(pokedexViewModel::retry)
    }
    else {
        val defaultGap = dimensionResource(id = R.dimen.default_gap)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            // bottom gap is handled by PokedexCard item
            contentPadding = PaddingValues(start = defaultGap, top = defaultGap, end = defaultGap)
        ) {
            items(pokedexViewModel.getPokedexList()) { item ->
                PokedexCard(item) {
                    startActivity(context, context.PokemonDetailIntent(item.pokemonId), null)
                }
            }
        }
    }
}

@Composable
fun PokedexCard(item: PokedexPresentationModel, onPokemonClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.default_gap))
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
                      getDrawableTypeIcon(item.pokemonTypes.mainType)
                  )
              )
        ) {
            val pokedexNumberSidePadding = dimensionResource(id = R.dimen.pokedex_number_side_padding)
            VerticalText(
                text = item.pokemonId.toString(),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = pokedexNumberSidePadding, end = pokedexNumberSidePadding),
                style = TextStyle(fontSize = 16.sp, color = colorResource(id = R.color.white)) // todo can we define this text style somewhere?
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
