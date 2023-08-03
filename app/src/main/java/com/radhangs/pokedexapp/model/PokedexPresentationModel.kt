package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.shared.capitalizeFirstLetter

// not terrible, could add some helper functions to help with ui
// this is essentially what will be used by the lazy list for the main page
data class PokedexPresentationModel(
    val pokemonId: Int,
    val pokemonName: String,
    val pokemonTypes: PokemonTypesPresentationModel,
    val spriteUri: String?
) {
    companion object
    {
        fun fromNetworkData(
            pokemon: PokedexQuery.Pokemon_v2_pokemon
        ) : PokedexPresentationModel =
            PokedexPresentationModel(
                pokemonId = pokemon.id,
                pokemonName = pokemon.name.capitalizeFirstLetter(),
                pokemonTypes = PokemonTypesPresentationModel.fromPokedexNetworkData(
                    pokemon.pokemon_v2_pokemontypes
                ),
                spriteUri = getFrontDefaultSprite(pokemon.pokemon_v2_pokemonsprites.first().sprites)
            )

        // create a test for if there's a map with no front_default key and see what it returns, make sure it doesn't break
        // TODO default to front_transparent, if it doesn't exist, do front_default, else just do the 0.png for a question mark sprite image
        fun getFrontDefaultSprite(input: String) = parseStringToMap(input)["front_default"] //front_default, front_transparent isn't for all pokemon

        // could easily make a bunch of tests for this
        fun parseStringToMap(input: String): Map<String, String> {
            val regex = """\s*"([^"]+)": "([^"]+)"""".toRegex()
            val matches = regex.findAll(input)

            val map = mutableMapOf<String, String>()
            for (match in matches) {
                val (key, value) = match.destructured
                map[key] = value
            }

            return map
        }
    }
}

