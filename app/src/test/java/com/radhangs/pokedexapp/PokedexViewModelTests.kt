package com.radhangs.pokedexapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.radhangs.pokedexapp.data.mockPokedexPresentationData
import com.radhangs.pokedexapp.pokedex.PokedexViewModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import com.radhangs.pokedexapp.shared.LoadingState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class PokedexViewModelTests {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule() // Rule for live data

    val mockPokedexRepository: PokedexRepository = mock()

    val mockSelectedPokemonRepository: SelectedPokemonRepository = mock()

    @Test
    fun `loading state set to initialized after successful response`()
    {
        val pokedexViewModel = object: PokedexViewModel(mockPokedexRepository, mockSelectedPokemonRepository) {
            override fun fetchData() {
                processPokedexResult(PokedexRepository.PokedexResult.Success(emptyList()))
            }
        }
        pokedexViewModel.fetchData()

        assertEquals(pokedexViewModel.viewState.value!!.loadingState, LoadingState.INITIALIZED)
    }

    @Test
    fun `loading state set to error after receiving error response`()
    {
        val pokedexViewModel = object: PokedexViewModel(mockPokedexRepository, mockSelectedPokemonRepository) {
            override fun fetchData() {
                processPokedexResult(PokedexRepository.PokedexResult.Error("Test error message"))
            }
        }
        pokedexViewModel.fetchData()

        assertEquals(pokedexViewModel.viewState.value!!.loadingState, LoadingState.ERROR)
    }

    @Test
    fun `loading state is set to uninitialized on init`()
    {
        val pokedexViewModel = PokedexViewModel(mockPokedexRepository, mockSelectedPokemonRepository)

        assertEquals(pokedexViewModel.viewState.value!!.loadingState, LoadingState.UNINITIALIZED)
    }

    @Test
    fun `Pokedex list is not empty after valid data in successful result`()
    {
        val pokedexViewModel = object: PokedexViewModel(mockPokedexRepository, mockSelectedPokemonRepository) {
            override fun fetchData() {
                processPokedexResult(
                    PokedexRepository.PokedexResult.Success(data = mockPokedexPresentationData)
                )
            }
        }
        pokedexViewModel.fetchData()

        assertEquals(mockPokedexPresentationData, pokedexViewModel.viewState.value!!.pokedexList)
    }

    @Test
    fun `loading state is set to uninitialized when calling retry`()
    {
        val pokedexViewModel = object: PokedexViewModel(mockPokedexRepository, mockSelectedPokemonRepository) {
            override fun fetchData() { }
        }

        pokedexViewModel.retry()

        assertEquals(pokedexViewModel.viewState.value!!.loadingState, LoadingState.UNINITIALIZED)
    }

    @Test
    fun `Selected pokemon repository has value when view model set selected pokemon is called`() {
        val selectedPokemonRepository = SelectedPokemonRepository()
        val pokedexViewModel = PokedexViewModel(mockPokedexRepository, selectedPokemonRepository)

        pokedexViewModel.setSelectedPokemon(1)

        assertEquals(selectedPokemonRepository.getSelectedPokemon().value, 1)
    }
}