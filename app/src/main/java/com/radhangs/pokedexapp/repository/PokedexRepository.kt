package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel

// make injectable? then I wouldn't need to pass it into the view models...
class PokedexRepository(private val apolloClient: ApolloClient) {
    private var pokedexPokemon: List<PokedexPresentationModel>? = null

    suspend fun fetchPokedex() {
        val response = apolloClient.query(PokedexQuery()).execute()
        val data = response.data
        data?.pokemon_v2_pokemon?.let { listOfPokemon ->
            pokedexPokemon = listOfPokemon.map {
                PokedexPresentationModel.fromNetworkData(it)
            }
        }
    }

    fun getPokedexPokemon() = pokedexPokemon ?: emptyList()
}