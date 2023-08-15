package com.radhangs.pokedexapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import coil.ImageLoader
import com.radhangs.pokedexapp.data.mockPokemonDetailPresentationData
import com.radhangs.pokedexapp.data.mockPokemonMovesPresentationData
import com.radhangs.pokedexapp.pokemondetail.PokemonDetailViewModel
import com.radhangs.pokedexapp.repository.PokemonDetailRepository
import com.radhangs.pokedexapp.repository.PokemonMovesRepository
import com.radhangs.pokedexapp.shared.LoadingState
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class PokemonDetailViewModelTests {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule() // Rule for live data

    val pokemonDetailRepository: PokemonDetailRepository = mock()

    val pokemonMovesRepository: PokemonMovesRepository = mock()

    val mockImageLoader: ImageLoader = mock()

    @Test
    fun `loading state set to initialized after successful pokemon details response`() {
        val pokemonDetailViewModel = object : PokemonDetailViewModel(
            pokemonDetailRepository,
            pokemonMovesRepository,
            mockImageLoader,
            pokemonId = 1
        ) {
            override fun fetchData() {
                processPokemonDetailsResult(
                    PokemonDetailRepository.PokemonDetailResult.Success(
                        mockPokemonDetailPresentationData
                    )
                )
            }
        }
        pokemonDetailViewModel.fetchData()

        Assert.assertEquals(
            pokemonDetailViewModel.viewState.value!!.loadingState,
            LoadingState.INITIALIZED
        )
    }

    @Test
    fun `loading state set to error after receiving pokemon details error response`() {
        val pokemonDetailViewModel = object : PokemonDetailViewModel(
            pokemonDetailRepository,
            pokemonMovesRepository,
            mockImageLoader,
            pokemonId = 1
        ) {
            override fun fetchData() {
                processPokemonDetailsResult(
                    PokemonDetailRepository.PokemonDetailResult.Error("Test error message")
                )
            }
        }
        pokemonDetailViewModel.fetchData()

        Assert.assertEquals(
            pokemonDetailViewModel.viewState.value!!.loadingState,
            LoadingState.ERROR
        )
    }

    @Test
    fun `loading state is set to uninitialized on init`() {
        val pokemonDetailViewModel = PokemonDetailViewModel(
            pokemonDetailRepository,
            pokemonMovesRepository,
            mockImageLoader,
            pokemonId = 1
        )

        Assert.assertEquals(
            pokemonDetailViewModel.viewState.value!!.loadingState,
            LoadingState.UNINITIALIZED
        )
    }

    @Test
    fun `Pokemon detail is not empty after valid data in successful pokemon details result`() {
        val pokemonDetailViewModel = object : PokemonDetailViewModel(
            pokemonDetailRepository,
            pokemonMovesRepository,
            mockImageLoader,
            pokemonId = 1
        ) {
            override fun fetchData() {
                processPokemonDetailsResult(
                    PokemonDetailRepository.PokemonDetailResult.Success(
                        mockPokemonDetailPresentationData
                    )
                )
            }
        }
        pokemonDetailViewModel.fetchData()

        Assert.assertEquals(
            mockPokemonDetailPresentationData,
            pokemonDetailViewModel.viewState.value!!.pokemonDetail
        )
    }

    @Test
    fun `loading state is set to uninitialized when calling retry`() {
        val pokemonDetailViewModel = object : PokemonDetailViewModel(
            pokemonDetailRepository,
            pokemonMovesRepository,
            mockImageLoader,
            pokemonId = 1
        ) {
            override fun fetchData() { }
        }

        pokemonDetailViewModel.retry()

        Assert.assertEquals(
            pokemonDetailViewModel.viewState.value!!.loadingState,
            LoadingState.UNINITIALIZED
        )
    }

    @Test
    fun `Pokemon moves list isn't empty after success result`() {
        val pokemonDetailViewModel = object : PokemonDetailViewModel(
            pokemonDetailRepository,
            pokemonMovesRepository,
            mockImageLoader,
            pokemonId = 1
        ) {
            override fun fetchData() {
                processPokemonMovesResult(
                    PokemonMovesRepository.PokemonMovesResult.Success(
                        mockPokemonMovesPresentationData
                    )
                )
            }
        }
        pokemonDetailViewModel.fetchData()

        Assert.assertEquals(
            mockPokemonMovesPresentationData,
            pokemonDetailViewModel.viewState.value!!.pokemonMoves
        )
    }
}
