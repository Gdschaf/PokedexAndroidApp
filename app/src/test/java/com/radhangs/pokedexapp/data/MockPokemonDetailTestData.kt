package com.radhangs.pokedexapp.data

import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.model.EvolutionChainPresentationModel
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.model.PokemonTypesPresentationModel
import com.radhangs.pokedexapp.model.PokemonType

const val mockPokemonId: Int = 1
const val mockPokemonName: String = "bulbasaur"

// out of order on purpose, the presentation order should be sorting by id
val mockNetworkEvolutionChain = PokemonDetailQuery.Pokemon_v2_evolutionchain(
    pokemon_v2_pokemonspecies = listOf(
        PokemonDetailQuery.Pokemon_v2_pokemonspecy1(
            name = "ivysaur",
            id = 2,
            pokemon_v2_pokemons = listOf(
                PokemonDetailQuery.Pokemon_v2_pokemon(
                    listOf(PokemonDetailQuery.Pokemon_v2_pokemonsprite(
                        sprites = "\"front_default\": \"/media/sprites/pokemon/2.png\""
                    ))
                )
            )
        ),
        PokemonDetailQuery.Pokemon_v2_pokemonspecy1(
            name = mockPokemonName,
            id = mockPokemonId,
            pokemon_v2_pokemons = listOf(
                PokemonDetailQuery.Pokemon_v2_pokemon(
                    listOf(PokemonDetailQuery.Pokemon_v2_pokemonsprite(
                        sprites = "\"front_default\": \"/media/sprites/pokemon/1.png\""
                    ))
                )
            )
        ),
        PokemonDetailQuery.Pokemon_v2_pokemonspecy1(
            name = "venusaur",
            id = 3,
            pokemon_v2_pokemons = listOf(
                PokemonDetailQuery.Pokemon_v2_pokemon(
                    listOf(PokemonDetailQuery.Pokemon_v2_pokemonsprite(
                        sprites = "\"front_default\": \"/media/sprites/pokemon/3.png\""
                    ))
                )
            )
        )
    )
)

val mockPresentationEvolutionChain = listOf(
    EvolutionChainPresentationModel(
        pokemonName = "Bulbasaur",
        pokemonId = mockPokemonId,
        spriteUri = "/media/sprites/pokemon/1.png"
    ),
    EvolutionChainPresentationModel(
        pokemonName = "Ivysaur",
        pokemonId = 2,
        spriteUri = "/media/sprites/pokemon/2.png"
    ),
    EvolutionChainPresentationModel(
        pokemonName = "Venusaur",
        pokemonId = 3,
        spriteUri = "/media/sprites/pokemon/3.png"
    )
)

val mockNetworkBaseStats = listOf(
    PokemonDetailQuery.Pokemon_v2_pokemonstat(
        base_stat = 45,
        pokemon_v2_stat = PokemonDetailQuery.Pokemon_v2_stat(name = "hp")
    ),
    PokemonDetailQuery.Pokemon_v2_pokemonstat(
        base_stat = 49,
        pokemon_v2_stat = PokemonDetailQuery.Pokemon_v2_stat(name = "attack")
    ),
    PokemonDetailQuery.Pokemon_v2_pokemonstat(
        base_stat = 49,
        pokemon_v2_stat = PokemonDetailQuery.Pokemon_v2_stat(name = "defense")
    ),
    PokemonDetailQuery.Pokemon_v2_pokemonstat(
        base_stat = 65,
        pokemon_v2_stat = PokemonDetailQuery.Pokemon_v2_stat(name = "special-attack")
    ),
    PokemonDetailQuery.Pokemon_v2_pokemonstat(
        base_stat = 65,
        pokemon_v2_stat = PokemonDetailQuery.Pokemon_v2_stat(name = "special-defense")
    ),
    PokemonDetailQuery.Pokemon_v2_pokemonstat(
        base_stat = 45,
        pokemon_v2_stat = PokemonDetailQuery.Pokemon_v2_stat(name = "speed")
    )
)

val mockPresentationBaseStats = mapOf(
    "Hp" to 45,
    "Attack" to 49,
    "Defense" to 49,
    "Special Attack" to 65,
    "Special Defense" to 65,
    "Speed" to 45
)

val mockPokemonDetailNetworkData = PokemonDetailQuery.Pokemon_v2_pokemon_by_pk(
    id = mockPokemonId,
    name = mockPokemonName,
    height = 7,
    weight = 69,
    pokemon_v2_pokemonstats = mockNetworkBaseStats,
    pokemon_v2_pokemontypes = listOf(
        PokemonDetailQuery.Pokemon_v2_pokemontype(PokemonDetailQuery.Pokemon_v2_type("grass")),
        PokemonDetailQuery.Pokemon_v2_pokemontype(PokemonDetailQuery.Pokemon_v2_type("poison"))
    ),
    pokemon_v2_pokemonspecy = PokemonDetailQuery.Pokemon_v2_pokemonspecy(
        base_happiness = 50,
        capture_rate = 45,
        pokemon_v2_evolutionchain = mockNetworkEvolutionChain
    )
)

val mockPokemonDetailPresentationData = PokemonDetailPresentationModel(
    pokemonId = mockPokemonId,
    pokemonName = "Bulbasaur",
    height = 0.7f,
    weight = 6.9f,
    types = PokemonTypesPresentationModel(PokemonType.GRASS, PokemonType.POISON),
    baseHappiness = 50,
    captureRate = 45,
    evolutionaryChain = mockPresentationEvolutionChain,
    baseStats = mockPresentationBaseStats
)

val mockPokemonDetailNullNetworkData = PokemonDetailQuery.Pokemon_v2_pokemon_by_pk(
    id = mockPokemonId,
    name = mockPokemonName,
    height = null,
    weight = null,
    pokemon_v2_pokemonstats = emptyList(),
    pokemon_v2_pokemontypes = emptyList(),
    pokemon_v2_pokemonspecy = null
)

val mockPokemonDetailNullPresentationData = PokemonDetailPresentationModel(
    pokemonId = mockPokemonId,
    pokemonName = "Bulbasaur",
    height = 0.0f,
    weight = 0.0f,
    types = PokemonTypesPresentationModel.empty,
    baseHappiness = 0,
    captureRate = 0,
    evolutionaryChain = emptyList(),
    baseStats = emptyMap()
)