package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.PokemonTypesQuery

// if we use dagger, we can easily make this injectable, then we wouldn't have to pass it into the view models...
// deprecated, we now pull the type data in with the pokemon data, come to think of it, that's probably slower...
class PokemonTypeRepository(private val apolloClient: ApolloClient) {
    private var pokemonTypeMap: Map<Int, String>? = null

    suspend fun fetchPokemonTypes() {
        val response = apolloClient.query(PokemonTypesQuery()).execute()
        val data = response.data
        data?.pokemon_v2_type?.let { listOfTypes ->
            pokemonTypeMap = listOfTypes.associate { it.id to it.name }
        }
    }

    fun getPokemonTypeById(typeId: Int): String = pokemonTypeMap?.get(typeId) ?: ""
}