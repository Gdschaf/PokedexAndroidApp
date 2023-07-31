package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.model.ConvertToUiModel
import com.radhangs.pokedexapp.model.PokedexPresentationModel

// make injectable?
class PokedexRepository(private val apolloClient: ApolloClient) {
    private var pokedexPokemon: List<PokedexPresentationModel>? = null

    suspend fun fetchPokedex(typeRepository: PokemonTypeRepository) {
        val response = apolloClient.query(PokedexQuery()).execute()
        val data = response.data
        data?.pokemon_v2_pokemonsprites?.let { listOfPokemon ->
            pokedexPokemon = ConvertToUiModel(listOfPokemon, typeRepository)
        }
    }

    fun getPokedexPokemon() = pokedexPokemon ?: emptyList()
}