package com.radhangs.pokedexapp.pokedex

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.pokemondetail.PokemonDetailIntent
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromUrl
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.PokemonTitle
import com.radhangs.pokedexapp.shared.apolloClient

@Composable
fun Pokedex(context: ComponentActivity) {
    val pokedexViewModel = ViewModelProvider(
        context,
        PokedexViewModelFactory(apolloClient())
    )[PokedexViewModel::class.java]

    if(pokedexViewModel.isLoading().value) {
        Loading()
    }
    else if(pokedexViewModel.hasError().value) {
        ErrorTryAgain(pokedexViewModel::retry)
    }
    else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            contentPadding = PaddingValues(16.dp)
        ) {
            items(pokedexViewModel.getLazyListData()) {item ->
                PokedexCard(item) {
                    startActivity(context, context.PokemonDetailIntent(item.pokemonId), null)
                }
            }
        }
    }
}

// this could be cleaned up tbh
@Composable
fun PokedexCard(item: PokedexPresentationModel, onPokemonClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp) // todo, add this padding somewhere, dimen?
            .fillMaxWidth()
            .clickable { onPokemonClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokedexSprite(spriteUri = item.spriteUri)
        PokemonTitle(pokemonName = item.pokemonName, pokemonTypes = item.pokemonTypes, modifier = Modifier.fillMaxWidth())
    }
}

// todo kind of wanna add a stroke around the sprite to make it look better? idk, try it.
@Composable
fun PokedexSprite(spriteUri: String?) {
    val spriteModifiier = Modifier.padding(end = 8.dp).size(dimensionResource(id = R.dimen.sprite_size))

    // the sprite paths are honestly, a bit wack.
    // this could be better but with how odd it is, I'm not sure it matters.
    val url = spriteUri?.replace("/media", Constants.SPRITE_DOMAIN) ?: Constants.SPRITE_UNKNOWN_URL

    ImageFromUrl(
        url = url,
        modifier = spriteModifiier,
        contentDescription = "Pokemon sprite" // todo add to strings
    )
}

// make reusable so we can use it on the details page
