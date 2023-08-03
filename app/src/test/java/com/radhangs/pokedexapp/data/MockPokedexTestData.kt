package com.radhangs.pokedexapp.data

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonTypesPresentationModel
import com.radhangs.pokedexapp.model.PokemonType

val mockGrassNetworkType = PokedexQuery.Pokemon_v2_pokemontype(PokedexQuery.Pokemon_v2_type(name = "grass"))
val mockPoisonNetworkType = PokedexQuery.Pokemon_v2_pokemontype(PokedexQuery.Pokemon_v2_type(name = "poison"))

val mockPresentationType = PokemonTypesPresentationModel(PokemonType.GRASS, PokemonType.POISON)

val mockPokedexNetworkData = listOf(
    PokedexQuery.Pokemon_v2_pokemon(
        id = 1,
        name = "bulbasaur",
        pokemon_v2_pokemonsprites = listOf(
            PokedexQuery.Pokemon_v2_pokemonsprite(sprites = "\"front_default\": \"/media/sprites/pokemon/1.png\"")
        ),
        pokemon_v2_pokemontypes = listOf(
            mockGrassNetworkType,
            mockPoisonNetworkType
        )
    )
)

val mockPokedexPresentationData = listOf(
    PokedexPresentationModel(
        pokemonId = 1,
        pokemonName = "Bulbasaur",
        pokemonTypes = mockPresentationType,
        spriteUri = "/media/sprites/pokemon/1.png"
    )
)