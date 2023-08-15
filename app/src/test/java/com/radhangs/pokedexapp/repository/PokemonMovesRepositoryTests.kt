package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.data.mockPokemonId
import com.radhangs.pokedexapp.data.mockPokemonMovesDuplicateNetworkData
import com.radhangs.pokedexapp.data.mockPokemonMovesNetworkData
import com.radhangs.pokedexapp.data.mockPokemonMovesNullNetworkData
import com.radhangs.pokedexapp.data.mockPokemonMovesPresentationData
import com.radhangs.pokedexapp.data.mockPokemonSwordsDancePresentationData
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PokemonMovesRepositoryTests {
    @OptIn(ApolloExperimental::class)
    private val testApolloClient = ApolloClient.Builder()
        .networkTransport(QueueTestNetworkTransport())
        .build()

    private lateinit var pokemonMovesRepository: PokemonMovesRepository

    @Before
    fun setup() {
        pokemonMovesRepository = PokemonMovesRepository(testApolloClient)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchMovesForPokemon with valid unsorted data`() {
        testApolloClient.enqueueTestResponse(
            PokemonMovesQuery(mockPokemonId),
            PokemonMovesQuery.Data(
                mockPokemonMovesNetworkData
            )
        )

        val result = runBlocking {
            pokemonMovesRepository.fetchMovesForPokemon(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonMovesRepository.PokemonMovesResult.Success)
        val successResult = result as PokemonMovesRepository.PokemonMovesResult.Success
        TestCase.assertEquals(mockPokemonMovesPresentationData, successResult.data)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokemonDetails with null data`() {
        testApolloClient.enqueueTestResponse(
            PokemonMovesQuery(mockPokemonId),
            PokemonMovesQuery.Data(
                mockPokemonMovesNullNetworkData
            )
        )

        val result = runBlocking {
            pokemonMovesRepository.fetchMovesForPokemon(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonMovesRepository.PokemonMovesResult.Success)
        val successResult = result as PokemonMovesRepository.PokemonMovesResult.Success
        TestCase.assertTrue(successResult.data.isEmpty())
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokemonDetails with no data`() {
        testApolloClient.enqueueTestResponse(
            PokemonMovesQuery(mockPokemonId),
            PokemonMovesQuery.Data(null)
        )

        val result = runBlocking {
            pokemonMovesRepository.fetchMovesForPokemon(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonMovesRepository.PokemonMovesResult.Error)
        val errorResult = result as PokemonMovesRepository.PokemonMovesResult.Error
        TestCase.assertEquals(
            "Pokemon moves query returned null data we weren't expecting",
            errorResult.errorMessage
        )
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokemonDetails with duplicate move data`() {
        testApolloClient.enqueueTestResponse(
            PokemonMovesQuery(mockPokemonId),
            PokemonMovesQuery.Data(mockPokemonMovesDuplicateNetworkData)
        )

        val result = runBlocking {
            pokemonMovesRepository.fetchMovesForPokemon(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonMovesRepository.PokemonMovesResult.Success)
        val successResult = result as PokemonMovesRepository.PokemonMovesResult.Success
        TestCase.assertEquals(mockPokemonSwordsDancePresentationData, successResult.data)
    }
}
