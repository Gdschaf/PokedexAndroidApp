package com.radhangs.pokedexapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonPresentationTypes

// could be used for testing, and should be
val test: List<PokedexPresentationModel> = listOf(
    PokedexPresentationModel(pokemonName = "Bulbasaur", pokemonPresentationTypes = PokemonPresentationTypes.empty),
    PokedexPresentationModel(pokemonName = "Ivysaur", pokemonPresentationTypes = PokemonPresentationTypes.empty),
    PokedexPresentationModel(pokemonName = "Venusaur", pokemonPresentationTypes = PokemonPresentationTypes.empty),
)

@Composable
fun Pokedex() {
    val pokedexViewModel = PokedexViewModel(apolloClient())

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(pokedexViewModel.getLazyListData()) {item ->
            PokedexCard(item)
        }
    }
}

@Composable
fun PokedexCard(item: PokedexPresentationModel) {
    Text(text = item.pokemonName)
}