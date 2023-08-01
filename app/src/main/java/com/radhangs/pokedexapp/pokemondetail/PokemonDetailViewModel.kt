package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.pokedex.PokedexViewModel
import java.lang.IllegalArgumentException

class PokemonDetailViewModel(private val apolloClient: ApolloClient) : ViewModel() {

    private val loading = mutableStateOf(true)
    private val error = mutableStateOf(false)
}

class PokemonDetailViewModelFactory(private val apolloClient: ApolloClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            return PokedexViewModel(apolloClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}