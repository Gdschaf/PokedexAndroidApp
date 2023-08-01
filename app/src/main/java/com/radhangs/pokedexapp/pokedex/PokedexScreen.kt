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
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonPresentationTypes
import com.radhangs.pokedexapp.model.getDrawableTypeIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.shared.ErrorTryAgain
import com.radhangs.pokedexapp.shared.ImageFromUrl
import com.radhangs.pokedexapp.shared.Loading
import com.radhangs.pokedexapp.shared.apolloClient

// could be used for testing, and should be
val test: List<PokedexPresentationModel> = listOf(
    PokedexPresentationModel(pokemonName = "Bulbasaur", pokemonPresentationTypes = PokemonPresentationTypes.empty, null),
    PokedexPresentationModel(pokemonName = "Ivysaur", pokemonPresentationTypes = PokemonPresentationTypes.empty, null),
    PokedexPresentationModel(pokemonName = "Venusaur", pokemonPresentationTypes = PokemonPresentationTypes.empty, null),
)

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
            modifier = Modifier.fillMaxWidth().clickable {  },
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
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(item.spriteUri != null)
        {
            val domain = "https://raw.githubusercontent.com/PokeAPI/sprites/master/" // define this somewhere?
            val url = item.spriteUri.replace("/media", domain) // the sprite paths are honestly, a bit wack.
            ImageFromUrl(
                url = url,
                modifier = Modifier.size(75.dp, 75.dp),
                contentDescription = "Pokemon sprite"
            )
        }
        Text(
            text = item.pokemonName,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            style = TextStyle(fontSize = 25.sp, color = colorResource(id = R.color.text_color)
        ))
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End
        ) {
            Image (
                painter = painterResource(getDrawableTypeIcon(item.pokemonPresentationTypes.mainType)),
                contentDescription = "something type", // replace something with the type that it is, or do some grouping for when there's two types
                modifier = Modifier.size(25.dp, 25.dp) // move to a sizes values resource file
            )
            if(item.pokemonPresentationTypes.secondaryType != null)
            {
                Image (
                    painter = painterResource(getDrawableTypeIcon(item.pokemonPresentationTypes.secondaryType)),
                    contentDescription = "something type", // replace something with the type that it is, or do some grouping for when there's two types
                    modifier = Modifier.size(25.dp, 25.dp), // move to a sizes values resource file
                )
            }
        }
    }
}