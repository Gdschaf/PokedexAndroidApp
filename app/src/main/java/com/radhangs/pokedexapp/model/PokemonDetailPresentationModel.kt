package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.shared.capitalizeFirstLetter

fun Int.divideByTen(): Float = this / 10f

fun PokemonDetailQuery.Pokemon_v2_pokemon_by_pk.toPresentationData() =
    PokemonDetailPresentationModel(
        pokemonId = id,
        pokemonName = name.capitalizeFirstLetter(),
        height = height?.divideByTen() ?: 0f,
        weight = weight?.divideByTen() ?: 0f,
        types = pokemon_v2_pokemontypes.detailToPresentationModel(),
        baseHappiness = pokemon_v2_pokemonspecy?.base_happiness ?: 0,
        captureRate = pokemon_v2_pokemonspecy?.capture_rate ?: 0,
        evolutionChain = pokemon_v2_pokemonspecy?.pokemon_v2_evolutionchain?.toPresentationList() ?: emptyList(),
        baseStats = pokemon_v2_pokemonstats
            .filter { it.pokemon_v2_stat != null }
            .associate { it.pokemon_v2_stat!!.name to it.base_stat }
    )

fun PokemonDetailQuery.Pokemon_v2_evolutionchain.toPresentationList() =
    this.pokemon_v2_pokemonspecies.let { list ->
        list.map { evo ->
            evo.toPresentationModel()
        }.sortedBy { it.pokemonId }
    }

data class PokemonDetailPresentationModel(
    val pokemonId: Int,
    val pokemonName: String,
    val height: Float, // this will be stored in meters, so 7 = 0.7m
    val weight: Float, // this will be stored in kg, so 69 = 6.9kg
    val types: PokemonTypesPresentationModel,
    val baseHappiness: Int,
    val captureRate: Int,
    val evolutionChain: List<EvolutionChainPresentationModel>, // sorted by pokedex number/pokemon id
    val baseStats: Map<String, Int>
) {
    companion object {
        val empty = PokemonDetailPresentationModel(
            pokemonId = 0,
            pokemonName = "",
            height = 0f,
            weight = 0f,
            types = PokemonTypesPresentationModel.empty,
            baseHappiness = 0,
            captureRate = 0,
            evolutionChain = emptyList(),
            baseStats = emptyMap()
        )
    }
}

fun PokemonDetailQuery.Pokemon_v2_pokemonspecy1.toPresentationModel() =
    EvolutionChainPresentationModel(
        pokemonName = name.capitalizeFirstLetter(),
        pokemonId = id,
        spriteUri = pokemon_v2_pokemons.first().pokemon_v2_pokemonsprites.first().sprites.parseToSpriteMap()["front_default"]
    )

data class EvolutionChainPresentationModel(val pokemonName: String, val pokemonId: Int, val spriteUri: String?)