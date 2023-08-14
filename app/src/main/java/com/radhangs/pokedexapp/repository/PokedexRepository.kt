package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PokedexRepository @Inject constructor(private val apolloClient: ApolloClient) {
    sealed class PokedexResult {
        data class Success(val data: List<PokedexPresentationModel>): PokedexResult()
        data class Error(val errorMessage: String): PokedexResult()
    }

    suspend fun fetchPokedex(): PokedexResult =
        try {
            val response = apolloClient.query(PokedexQuery()).execute()
            if (response.hasErrors()) {
                PokedexResult.Error(errorMessage = "Pokedex query response included errors")
            } else {
                response.data?.pokemon_v2_pokemon?.let { listOfPokemon ->
                    PokedexResult.Success(
                        data = listOfPokemon.map {
                            PokedexPresentationModel.fromNetworkData(it)
                        }
                    )
                } ?: PokedexResult.Error(errorMessage = "Response data returned null")
            }
        } catch (e: ApolloException) {
            PokedexResult.Error(errorMessage = "Apollo Exception: ${e.message}")
        }
}