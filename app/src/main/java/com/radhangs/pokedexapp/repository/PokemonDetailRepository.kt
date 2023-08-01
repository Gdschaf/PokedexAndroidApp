package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel

class PokemonDetailRepository(private val apolloClient: ApolloClient) {
    private var pokemonDetails: PokemonDetailPresentationModel? = null

    suspend fun fetchPokemonDetails(pokemonId: Int) {
        val response = apolloClient.query(PokemonDetailQuery(pokemonId)).execute()
        val data = response.data
        data?.pokemon_v2_pokemon_by_pk?.let { pokemonData ->
            // pokemonDetails = pokemonData // todo convert this to presetnation data, please.
            pokemonDetails = PokemonDetailPresentationModel.fromNetworkData(pokemonData)
        }
    }

    fun getPokemonDetails() = pokemonDetails
}