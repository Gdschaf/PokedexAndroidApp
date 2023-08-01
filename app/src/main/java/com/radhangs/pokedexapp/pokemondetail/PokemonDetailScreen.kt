package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.radhangs.pokedexapp.shared.apolloClient

@Composable
fun PokemonDetailScreen(context: ViewModelStoreOwner) {
    val pokemonDetailViewModel = ViewModelProvider(context, PokemonDetailViewModelFactory(
        apolloClient()
    )
    ).get(PokemonDetailViewModel::class.java)

    Column {
        ToolbarWithBackButton("Back", { })
        PokemonDetail()
    }
}

@Composable
fun PokemonDetail() {

}

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