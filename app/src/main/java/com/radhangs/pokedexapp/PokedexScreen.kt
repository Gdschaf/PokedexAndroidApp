package com.radhangs.pokedexapp

import android.content.Context
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonPresentationTypes
import com.radhangs.pokedexapp.model.getDrawableTypeIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

// could be used for testing, and should be
val test: List<PokedexPresentationModel> = listOf(
    PokedexPresentationModel(pokemonName = "Bulbasaur", pokemonPresentationTypes = PokemonPresentationTypes.empty, null),
    PokedexPresentationModel(pokemonName = "Ivysaur", pokemonPresentationTypes = PokemonPresentationTypes.empty, null),
    PokedexPresentationModel(pokemonName = "Venusaur", pokemonPresentationTypes = PokemonPresentationTypes.empty, null),
)

@Composable
fun Pokedex(context: ViewModelStoreOwner) {
    val pokedexViewModel = ViewModelProvider(context, PokedexViewModelFactory(apolloClient())).get(PokedexViewModel::class.java)

    if(pokedexViewModel.isLoading().value) {
        Loading()
    }
    else if(pokedexViewModel.hasError().value) {
        ErrorTryAgain(pokedexViewModel::retry)
    }
    else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
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

// reusable
@Composable
fun ImageFromUrl(url: String, modifier: Modifier, contentDescription: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

// reusable on the other page as well
@Composable
fun Loading() {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp, 50.dp),
            // color = colorResource(id = R.color.primary_color) //kinda silly, kinda cool, idk
        ) //put into values resource file
        Text(
            text = "Loading...",
            modifier = Modifier.padding(start = 16.dp),
            style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 25.sp)
        )
    }
}

// also reusable
@Composable
fun ErrorTryAgain(onRetryClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        // show an exclamation point
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.alert_circle),
            modifier = Modifier.size(width = 100.dp, height = 100.dp), // TODO add this to the values xml
            colorFilter = ColorFilter.tint(colorResource(id = R.color.primary_color))
        )
        // show an error: something went wrong
        Text(
            text = "Something went wrong.",
            style = TextStyle(fontSize = 20.sp, color = colorResource(id = R.color.text_color)),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // TODO add this to the values xml
        )
        // show a retry button, figure out callbacks for that
        Button(onClick = { onRetryClicked() } ) {
            Text(
                text = "Retry"
            )
        }
    }
}