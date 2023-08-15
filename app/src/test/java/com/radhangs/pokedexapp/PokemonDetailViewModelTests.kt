package com.radhangs.pokedexapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

class PokemonDetailViewModelTests {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule() // Rule for live data
}