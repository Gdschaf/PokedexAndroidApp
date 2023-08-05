package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel.Companion.getFrontDefaultSprite
import com.radhangs.pokedexapp.shared.capitalizeFirstLetter

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

        fun fromNetworkData(pokemonData: PokemonDetailQuery.Pokemon_v2_pokemon_by_pk) =
            PokemonDetailPresentationModel(
                pokemonId = pokemonData.id,
                pokemonName = pokemonData.name.capitalizeFirstLetter(),
                height = divideByTen(pokemonData.height),
                weight = divideByTen(pokemonData.weight),
                types = PokemonTypesPresentationModel.fromDetailsNetworkData(pokemonData.pokemon_v2_pokemontypes),
                baseHappiness = pokemonData.pokemon_v2_pokemonspecy?.base_happiness ?: 0,
                captureRate = pokemonData.pokemon_v2_pokemonspecy?.capture_rate ?: 0,
                evolutionChain = buildEvolutionChain(pokemonData.pokemon_v2_pokemonspecy?.pokemon_v2_evolutionchain),
                baseStats = pokemonData.pokemon_v2_pokemonstats
                    .filter { it.pokemon_v2_stat != null }
                    .associate { it.pokemon_v2_stat!!.name to it.base_stat }
            )

        fun divideByTen(value: Int?) =
            value?.let {
                value / 10.0f
            } ?: 0.0f

        fun buildEvolutionChain(evolutionaryChain: PokemonDetailQuery.Pokemon_v2_evolutionchain?) =
            evolutionaryChain?.pokemon_v2_pokemonspecies?.let {list ->
                list.map { evo ->
                    EvolutionChainPresentationModel.fromNetworkData(evo)
                }.sortedBy { it.pokemonId }
            } ?: emptyList()
    }
}

data class EvolutionChainPresentationModel(val pokemonName: String, val pokemonId: Int, val spriteUri: String?) {
    companion object {
        fun fromNetworkData(evo: PokemonDetailQuery.Pokemon_v2_pokemonspecy1) =
            EvolutionChainPresentationModel(
                pokemonName = evo.name.capitalizeFirstLetter(),
                pokemonId = evo.id,
                spriteUri = getFrontDefaultSprite(evo.pokemon_v2_pokemons.first().pokemon_v2_pokemonsprites.first().sprites)
            )
    }
}