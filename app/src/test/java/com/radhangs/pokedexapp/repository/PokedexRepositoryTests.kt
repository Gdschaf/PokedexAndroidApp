package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.data.mockPokedexNetworkData
import com.radhangs.pokedexapp.data.mockPokedexPresentationData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PokedexRepositoryTests {

    @OptIn(ApolloExperimental::class)
    private val testApolloClient = ApolloClient.Builder()
        .networkTransport(QueueTestNetworkTransport())
        .build()

    private lateinit var pokedexRepository: PokedexRepository

    @Before
    fun setup() {
        pokedexRepository = PokedexRepository(testApolloClient)
    }

    // I wanted to add two more tests for testing server and network issues and checking their responses
    // but Apollo's testing is still experimental and not very well fleshed out
    // you can read more about it here: https://www.apollographql.com/docs/kotlin/testing/mocking-graphql-responses/

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokedex with valid data`() {
        testApolloClient.enqueueTestResponse(PokedexQuery(), PokedexQuery.Data(
            mockPokedexNetworkData
        ))

        val result = runBlocking {
            pokedexRepository.fetchPokedex()
        }

        assertTrue(result is PokedexRepository.PokedexResult.Success)
        val successResult = result as PokedexRepository.PokedexResult.Success
        assertEquals(mockPokedexPresentationData, successResult.data)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchPokedex with empty list`() {
        testApolloClient.enqueueTestResponse(PokedexQuery(), PokedexQuery.Data(emptyList()))

        val result = runBlocking {
            pokedexRepository.fetchPokedex()
        }

        assertTrue(result is PokedexRepository.PokedexResult.Success)
        val successResult = result as PokedexRepository.PokedexResult.Success
        assertEquals(successResult.data, emptyList<PokedexPresentationModel>())
    }
}