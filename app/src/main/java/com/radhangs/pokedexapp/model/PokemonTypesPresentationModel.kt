package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokemonDetailQuery

fun List<PokemonDetailQuery.Pokemon_v2_pokemontype>.detailToPresentationModel() =
    PokemonTypesPresentationModel(
        mainType = getOrNull(0)?.pokemon_v2_type?.let { type ->
            PokemonTypeWithResources.getType(type.name)
        } ?: PokemonTypeWithResources.unknown,
        secondaryType = getOrNull(1)?.pokemon_v2_type?.let { type ->
            PokemonTypeWithResources.getType(type.name)
        }
    )

fun List<PokedexQuery.Pokemon_v2_pokemontype>.pokedexToPresentationModel() =
    PokemonTypesPresentationModel(
        mainType = getOrNull(0)?.pokemon_v2_type?.let { type ->
            PokemonTypeWithResources.getType(type.name)
        } ?: PokemonTypeWithResources.unknown,
        secondaryType = getOrNull(1)?.pokemon_v2_type?.let { type ->
            PokemonTypeWithResources.getType(type.name)
        }
    )

data class PokemonTypesPresentationModel(
    val mainType: PokemonTypeWithResources,
    val secondaryType: PokemonTypeWithResources? = null
) {
    companion object {
        val empty = PokemonTypesPresentationModel(PokemonTypeWithResources.unknown)
    }
}
