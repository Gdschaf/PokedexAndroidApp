package com.radhangs.pokedexapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.radhangs.pokedexapp.data.mockPokedexPresentationData
import com.radhangs.pokedexapp.pokedex.PokedexViewModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokedexViewModelTests {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule() // Rule for live data

    @Test
    fun `Loading and retry false after successful response`()
    {
        val pokedexViewModel = object: PokedexViewModel(mock(), mock()) {
            override fun fetchData() {
                processPokedexResult(PokedexRepository.PokedexResult.Success(emptyList()))
            }
        }
        pokedexViewModel.fetchData()

        assertFalse(pokedexViewModel.viewState.value!!.loading)
        assertFalse(pokedexViewModel.viewState.value!!.error)
    }

    @Test
    fun `Error is true and loading is false when getting an error response`()
    {
        val pokedexViewModel = object: PokedexViewModel(mock(), mock()) {
            override fun fetchData() {
                processPokedexResult(PokedexRepository.PokedexResult.Error("Test error message"))
            }
        }
        pokedexViewModel.fetchData()

        assertFalse(pokedexViewModel.viewState.value!!.loading)
        assertTrue(pokedexViewModel.viewState.value!!.error)
    }

    @Test
    fun `Loading is true and error is false on init of view model`()
    {
        val pokedexViewModel = object: PokedexViewModel(mock(), mock()) {
            override fun fetchData() { }
        }

        assertTrue(pokedexViewModel.viewState.value!!.loading)
        assertFalse(pokedexViewModel.viewState.value!!.error)
    }

    @Test
    fun `Pokedex list is not empty after valid data in successful result`()
    {
        val pokedexViewModel = object: PokedexViewModel(mock(), mock()) {
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
    fun `Loading is true and error is false when calling retry`()
    {
        val pokedexViewModel = object: PokedexViewModel(mock(), mock()) {
            override fun fetchData() { }
        }

        pokedexViewModel.retry()

        assertTrue(pokedexViewModel.viewState.value!!.loading)
        assertFalse(pokedexViewModel.viewState.value!!.error)
    }
}