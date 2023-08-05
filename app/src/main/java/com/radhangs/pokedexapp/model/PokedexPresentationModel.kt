package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.shared.capitalizeFirstLetter

// data model used by the presentation, holds the data we need. nothing more, nothing less.
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

        fun getFrontDefaultSprite(input: String) = parseStringToMap(input)["front_default"] ?: "0.png"

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

