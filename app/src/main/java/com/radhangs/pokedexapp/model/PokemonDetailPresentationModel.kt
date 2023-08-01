package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokemonDetailQuery

data class PokemonDetailPresentationModel(
    val pokemonId: Int,
    val pokemonName: String,
    val height: Float, // this will be stored in meters, so 7 = 0.7m
    val weight: Float, // this will be stored in kg, so 69 = 6.9kg
    val types: PokemonPresentationTypes,
    val baseHappiness: Int,
    val captureRate: Int,
    val evolutionaryChain: List<EvolutionChainPresentationModel>, //lets sort this in order of id, lowest to highest
    val baseStats: Map<String, Int>
) {
    companion object {
        val bulbasaur = PokemonDetailPresentationModel(1, "Bulbasaur", 0.7f, 6.9f, PokemonPresentationTypes(PokemonType.GRASS, PokemonType.POISON), 50, 50, emptyList(), emptyMap())

        fun fromNetworkData(pokemonData: PokemonDetailQuery.Pokemon_v2_pokemon_by_pk) =
            PokemonDetailPresentationModel(
                pokemonId = pokemonData.id,
                pokemonName = pokemonData.name,
                height = divideByTen(pokemonData.height),
                weight = divideByTen(pokemonData.weight),
                types = PokemonPresentationTypes.fromDetailsNetworkData(pokemonData.pokemon_v2_pokemontypes),
                baseHappiness = 0, // pokemonData.pokemon_v2_pokemonspecy.base_happiness,
                captureRate = 0,
                evolutionaryChain = emptyList(),
                baseStats = emptyMap()
            )
    }
}

fun divideByTen(value: Int?) =
    value?.let {
        value / 10.0f
    } ?: 0.0f

// might wanna add the sprite onto this
class EvolutionChainPresentationModel(val pokemonName: String, val pokemonId: Int, val spriteUri: String?) {
    companion object {
        fun fromNetworkData() {

        }
    }
}