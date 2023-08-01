package com.radhangs.pokedexapp.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonPresentationTypes
import com.radhangs.pokedexapp.model.getDrawableTypeIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromUrl
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.apolloClient

@Composable
fun Pokedex(context: ViewModelStoreOwner) {
    val pokedexViewModel = ViewModelProvider(context, PokedexViewModelFactory(apolloClient())).get(
        PokedexViewModel::class.java)

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
                PokedexCard(item)
            }
        }
    }
}

// this could be cleaned up tbh
@Composable
fun PokedexCard(item: PokedexPresentationModel) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp) // todo, add this padding somewhere, dimen?
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokedexSprite(spriteUri = item.spriteUri)
        Text(
            text = item.pokemonName,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp), // todo figure out padding and fine it somewhere
            style = TextStyle(fontSize = 25.sp, color = colorResource(id = R.color.text_color) // todo fine a place to define text sizes that isn't the dimen file
        ))
        PokemonTypes(item.pokemonPresentationTypes)
    }
}

// todo kind of wanna add a stroke around the sprite to make it look better? idk, try it.
@Composable
fun PokedexSprite(spriteUri: String?) {
    val spriteModifiier = Modifier.size(dimensionResource(id = R.dimen.sprite_size))
    if(spriteUri != null) {

        // the sprite paths are honestly, a bit wack.
        // this could be better but with how odd it is, I'm not sure it matters.
        val url = spriteUri.replace("/media", Constants.SPRITE_DOMAIN)

        ImageFromUrl(
            url = url,
            modifier = spriteModifiier,
            contentDescription = "Pokemon sprite" // todo add to strings
        )
    } else {
        ImageFromUrl(
            url = Constants.SPRITE_UNKNOWN_URL,
            modifier = spriteModifiier,
            contentDescription = "Unknown Pokemon sprite" // todo add to strings
        )
    }
}

@Composable
fun PokemonTypes(pokemonPresentationTypes: PokemonPresentationTypes) {
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        Image (
            painter = painterResource(getDrawableTypeIcon(pokemonPresentationTypes.mainType)),
            contentDescription = "something type", // todo, add content description for the types
            modifier = Modifier.size(dimensionResource(id = R.dimen.type_icon_size)),
        )
        if(pokemonPresentationTypes.secondaryType != null)
        {
            Image (
                painter = painterResource(getDrawableTypeIcon(pokemonPresentationTypes.secondaryType)),
                contentDescription = "something type", // todo, add content description for the types
                modifier = Modifier.size(dimensionResource(id = R.dimen.type_icon_size)),
            )
        }
    }
}