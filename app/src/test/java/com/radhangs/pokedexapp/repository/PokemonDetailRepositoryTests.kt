package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.data.mockPokemonDetailNetworkData
import com.radhangs.pokedexapp.data.mockPokemonDetailNullNetworkData
import com.radhangs.pokedexapp.data.mockPokemonDetailNullPresentationData
import com.radhangs.pokedexapp.data.mockPokemonDetailPresentationData
import com.radhangs.pokedexapp.data.mockPokemonId
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PokemonDetailRepositoryTests {
    @OptIn(ApolloExperimental::class)
    private val testApolloClient = ApolloClient.Builder()
        .networkTransport(QueueTestNetworkTransport())
        .build()

    private lateinit var pokemonDetailRepository: PokemonDetailRepository

    @Before
    fun setup() {
        pokemonDetailRepository = PokemonDetailRepository(testApolloClient)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokemonDetails with valid data`() {
        testApolloClient.enqueueTestResponse(
            PokemonDetailQuery(mockPokemonId), PokemonDetailQuery.Data(
                mockPokemonDetailNetworkData
        ))

        val result = runBlocking {
            pokemonDetailRepository.fetchPokemonDetails(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonDetailRepository.PokemonDetailResult.Success)
        val successResult = result as PokemonDetailRepository.PokemonDetailResult.Success
        TestCase.assertEquals(mockPokemonDetailPresentationData, successResult.data)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokemonDetails with null data`() {
        testApolloClient.enqueueTestResponse(
            PokemonDetailQuery(mockPokemonId), PokemonDetailQuery.Data(
                mockPokemonDetailNullNetworkData
            ))

        val result = runBlocking {
            pokemonDetailRepository.fetchPokemonDetails(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonDetailRepository.PokemonDetailResult.Success)
        val successResult = result as PokemonDetailRepository.PokemonDetailResult.Success
        TestCase.assertEquals(mockPokemonDetailNullPresentationData, successResult.data)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokemonDetails with no data`() {
        testApolloClient.enqueueTestResponse(PokemonDetailQuery(mockPokemonId), PokemonDetailQuery.Data(null))

        val result = runBlocking {
            pokemonDetailRepository.fetchPokemonDetails(mockPokemonId)
        }

        TestCase.assertTrue(result is PokemonDetailRepository.PokemonDetailResult.Error)
        val errorResult = result as PokemonDetailRepository.PokemonDetailResult.Error
        TestCase.assertEquals(errorResult.errorMessage, "Pokemon detail query returned null data we weren't expecting")
    }
}