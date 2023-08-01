package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.model.PokemonMovePresentationData

class PokemonMovesRepository(private val apolloClient: ApolloClient) {
    private var pokemonMoves: List<PokemonMovePresentationData>? = null

    suspend fun fetchMovesForPokemon(pokemonId: Int) {
        val response = apolloClient.query(PokemonMovesQuery(pokemonId)).execute()
        val data = response.data
        data?.pokemon_v2_pokemon_by_pk?.let { pokemonData ->
            for(move in pokemonData.pokemon_v2_pokemonmoves)
            {
                // todo please convert move data into move presentation data
                // todo we'll need to remove duplicates (by name), and sort them (by type, level up first, then TM/HM, then egg moves, etc, and sort the level up by level learned
            }
        }
    }

    fun getPokemonMoves() = pokemonMoves
}