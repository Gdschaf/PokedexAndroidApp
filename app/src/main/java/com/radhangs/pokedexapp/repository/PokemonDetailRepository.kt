package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PokemonDetailRepository @Inject constructor(private val apolloClient: ApolloClient) {
    sealed class PokemonDetailResult {
        data class Success(val data: PokemonDetailPresentationModel): PokemonDetailResult()
        data class Error(val errorMessage: String): PokemonDetailResult()
    }

    suspend fun fetchPokemonDetails(pokemonId: Int): PokemonDetailResult =
        try {
            val response = apolloClient.query(PokemonDetailQuery(pokemonId)).execute()
            if (response.hasErrors()) {
                PokemonDetailResult.Error(
                    errorMessage = "Pokemon detail query response included errors"
                )
            } else {
                response.data?.pokemon_v2_pokemon_by_pk?.let { pokemonData ->
                    PokemonDetailResult.Success(
                        data = PokemonDetailPresentationModel.fromNetworkData(pokemonData)
                    )
                } ?: PokemonDetailResult.Error(
                    errorMessage = "Pokemon detail query returned null data we weren't expecting"
                )
            }
        } catch (e: ApolloException) {
            PokemonDetailResult.Error(errorMessage = "Apollo Exception: ${e.message}")
        }
}