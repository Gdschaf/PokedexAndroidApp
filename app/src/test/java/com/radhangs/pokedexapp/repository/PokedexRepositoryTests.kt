package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokemonTypesQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.shared.mockNetworkData
import com.radhangs.pokedexapp.shared.mockPresentationData
import com.radhangs.pokedexapp.shared.mockTypeData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PokedexRepositoryTests {

    @OptIn(ApolloExperimental::class)
    private val testApolloClient = ApolloClient.Builder()
        .networkTransport(QueueTestNetworkTransport())
        .build()

    private lateinit var pokedexRepository: PokedexRepository
    private lateinit var pokemonTypeRepository: PokemonTypeRepository

    @Before
    fun setup() {
        pokedexRepository = PokedexRepository(testApolloClient)
        pokemonTypeRepository = PokemonTypeRepository(testApolloClient)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchData with valid data`() {
        testApolloClient.enqueueTestResponse(PokemonTypesQuery(), PokemonTypesQuery.Data(mockTypeData))
        testApolloClient.enqueueTestResponse(PokedexQuery(), PokedexQuery.Data(mockNetworkData))

        runBlocking {
            pokemonTypeRepository.fetchPokemonTypes()
            pokedexRepository.fetchPokedex(pokemonTypeRepository)
        }

        assertEquals(mockPresentationData, pokedexRepository.getPokedexPokemon())
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `test fetchData with no data`() {
        testApolloClient.enqueueTestResponse(PokedexQuery(), PokedexQuery.Data(emptyList()))

        runBlocking {
            pokedexRepository.fetchPokedex(pokemonTypeRepository)
        }

        assertEquals(pokedexRepository.getPokedexPokemon(), emptyList<PokedexPresentationModel>())
    }
}