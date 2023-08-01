package com.radhangs.pokedexapp.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokemonTypesQuery
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.model.PokemonPresentationTypes
import com.radhangs.pokedexapp.model.PokemonType
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
        val mockNetworkData = listOf(
            PokedexQuery.Pokemon_v2_pokemon(
                id = 1,
                name = "bulbasaur",
                pokemon_v2_pokemonsprites = listOf(
                    PokedexQuery.Pokemon_v2_pokemonsprite(sprites = "\"front_default\": \"/media/sprites/pokemon/1.png\"")
                ),
                pokemon_v2_pokemontypes = listOf(
                    PokedexQuery.Pokemon_v2_pokemontype(type_id = 4),
                    PokedexQuery.Pokemon_v2_pokemontype(type_id = 12)
                )
            )
        )

        val mockTypeData = listOf(
            PokemonTypesQuery.Pokemon_v2_type(
                name = "grass",
                id = 4
            ),
            PokemonTypesQuery.Pokemon_v2_type(
                name = "poison",
                id = 12
            )
        )

        val mockPresentationData = listOf(
            PokedexPresentationModel(
                pokemonName = "Bulbasaur",
                pokemonPresentationTypes = PokemonPresentationTypes(PokemonType.GRASS, PokemonType.POISON),
                spriteUri = "/media/sprites/pokemon/1.png"
            )
        )

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