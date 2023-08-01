package com.radhangs.pokedexapp.pokemondetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.ui.theme.PokedexAppTheme

fun Context.PokemonDetailIntent(pokemonId: Int): Intent =
    Intent(this, PokemonDetailActivity::class.java).apply {
        putExtra(PokemonDetailActivity.POKEMON_ID, pokemonId)
    }

class PokemonDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonId = intent.getIntExtra(POKEMON_ID, 1) // we'll default to Bulbasaur but probably should handle errors

        setContent {
            PokedexAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.background_primary)
                ) {
                    PokemonDetailScreen(context = this, pokemonId = pokemonId)
                }
            }
        }
    }

    companion object {
        val POKEMON_ID = "pokemon_id"
    }
}