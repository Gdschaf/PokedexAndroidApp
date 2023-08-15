package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.shared.capitalizeFirstLetter

fun PokedexQuery.Pokemon_v2_pokemon.toPresentationModel() =
    PokedexPresentationModel(
        pokemonId = id,
        pokemonName = name.capitalizeFirstLetter(),
        pokemonTypes = pokemon_v2_pokemontypes.pokedexToPresentationModel(),
        spriteUri = pokemon_v2_pokemonsprites.first().sprites.parseToSpriteMap()["front_default"]
    )

fun String.parseToSpriteMap(): Map<String, String> {
    val regex = """\s*"([^"]+)": "([^"]+)"""".toRegex()
    val matches = regex.findAll(this)

    val map = mutableMapOf<String, String>()
    for (match in matches) {
        val (key, value) = match.destructured
        map[key] = value
    }

    return map
}

// data model used by the presentation, holds the data we need. nothing more, nothing less.
data class PokedexPresentationModel(
    val pokemonId: Int,
    val pokemonName: String,
    val pokemonTypes: PokemonTypesPresentationModel,
    val spriteUri: String?
)