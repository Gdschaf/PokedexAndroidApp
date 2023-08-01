package com.radhangs.pokedexapp.shared

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonPresentationTypes
import com.radhangs.pokedexapp.model.PokemonType

// TODO go through and rename this stuff to something a bit more descriptive

val mockNetworkData = listOf(
    PokedexQuery.Pokemon_v2_pokemon(
        id = 1,
        name = "bulbasaur",
        pokemon_v2_pokemonsprites = listOf(
            PokedexQuery.Pokemon_v2_pokemonsprite(sprites = "\"front_default\": \"/media/sprites/pokemon/1.png\"")
        ),
        pokemon_v2_pokemontypes = listOf(
            PokedexQuery.Pokemon_v2_pokemontype(PokedexQuery.Pokemon_v2_type(name = "grass")),
            PokedexQuery.Pokemon_v2_pokemontype(PokedexQuery.Pokemon_v2_type(name = "poison"))
        )
    )
)

val mockPresentationData = listOf(
    PokedexPresentationModel(
        pokemonId = 1,
        pokemonName = "Bulbasaur",
        pokemonTypes = PokemonPresentationTypes(PokemonType.GRASS, PokemonType.POISON),
        spriteUri = "/media/sprites/pokemon/1.png"
    )
)

// could be used for testing, and should be
val test: List<PokedexPresentationModel> = listOf(
    PokedexPresentationModel(pokemonId = 1, pokemonName = "Bulbasaur", pokemonTypes = PokemonPresentationTypes.empty, null),
    PokedexPresentationModel(pokemonId = 1, pokemonName = "Ivysaur", pokemonTypes = PokemonPresentationTypes.empty, null),
    PokedexPresentationModel(pokemonId = 1, pokemonName = "Venusaur", pokemonTypes = PokemonPresentationTypes.empty, null),
)