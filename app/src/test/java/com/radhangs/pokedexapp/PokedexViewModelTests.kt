package com.radhangs.pokedexapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.radhangs.pokedexapp.pokedex.PokedexViewModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokedexViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Test loading and retry false after successful response`()
    {
        val pokedexRepository = mock<PokedexRepository>()
        val pokedexViewModel: PokedexViewModel

        runBlocking {
            `when`(pokedexRepository.fetchPokedex())
                .thenReturn(PokedexRepository.PokedexResult.Success(emptyList()))

            pokedexViewModel = PokedexViewModel(pokedexRepository, mock())

            delay(1)
        }

        val loading = pokedexViewModel.viewState.value!!.loading
        val error = pokedexViewModel.viewState.value!!.error

        assertFalse(loading)
        assertFalse(error)
    }

    @Test
    fun `Test error is false and loading is true when getting an error response`()
    {
        val pokedexRepository = mock<PokedexRepository>()
        val pokedexViewModel: PokedexViewModel

        runBlocking {
            `when`(pokedexRepository.fetchPokedex())
                .thenReturn(PokedexRepository.PokedexResult.Error("Test error message"))

            pokedexViewModel = PokedexViewModel(pokedexRepository, mock())

            delay(1)
        }

        val loading = pokedexViewModel.viewState.value!!.loading
        val error = pokedexViewModel.viewState.value!!.error

        assertFalse(loading)
        assertTrue(error)
    }

    @Test
    fun `Test loading is true and error is false while waiting on a response`()
    {
        val pokedexRepository = mock<PokedexRepository>()
        val pokedexViewModel: PokedexViewModel

        runBlocking {
            `when`(pokedexRepository.fetchPokedex())
                .thenReturn(PokedexRepository.PokedexResult.Success(emptyList()))

            pokedexViewModel = PokedexViewModel(pokedexRepository, mock())

            //don't delay here
        }

        val loading = pokedexViewModel.viewState.value!!.loading
        val error = pokedexViewModel.viewState.value!!.error

        assertTrue(loading)
        assertFalse(error)
    }
}