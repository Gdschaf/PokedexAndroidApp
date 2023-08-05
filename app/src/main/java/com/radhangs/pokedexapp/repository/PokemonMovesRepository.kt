package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel

class PokemonMovesRepository(private val apolloClient: ApolloClient) {
    sealed class PokemonMovesResult {
        data class Success(val data: List<PokemonMovePresentationModel>): PokemonMovesResult()
        data class Error(val errorMessage: String): PokemonMovesResult()
    }

    private val moveComparator = Comparator<PokemonMovePresentationModel> { obj1, obj2 ->
        when {
            obj1.learnLevel == null && obj2.learnLevel == null -> 0
            obj1.learnLevel == null -> 1
            obj2.learnLevel == null -> -1
            else -> obj1.learnLevel.compareTo(obj2.learnLevel)
        }
    }

    suspend fun fetchMovesForPokemon(pokemonId: Int) =
        try {
            val response = apolloClient.query(PokemonMovesQuery(pokemonId)).execute()
            if (response.hasErrors()) {
                PokemonMovesResult.Error(
                    errorMessage = "Pokemon moves query response included errors"
                )
            } else {
                response.data?.pokemon_v2_pokemon_by_pk?.let { pokemon ->
                    PokemonMovesResult.Success(
                        data = pokemon.pokemon_v2_pokemonmoves
                            .map { PokemonMovePresentationModel.fromNetworkData(it) }
                            .filter { it.moveName.isNotEmpty() }
                            .distinctBy { it.moveName }
                            .sortedWith(moveComparator)
                    )
                } ?: PokemonMovesResult.Error(
                    errorMessage = "Pokemon moves query returned null data we weren't expecting"
                )
            }
        } catch (e: ApolloException) {
            PokemonMovesResult.Error(errorMessage = "Apollo Exception: ${e.message}")
        }
}