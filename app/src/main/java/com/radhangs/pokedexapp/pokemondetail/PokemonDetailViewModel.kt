package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationData
import com.radhangs.pokedexapp.repository.PokemonDetailRepository
import com.radhangs.pokedexapp.repository.PokemonMovesRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PokemonDetailViewModel(
    private val apolloClient: ApolloClient,
    private val pokemonId: Int
) : ViewModel() {
    private val pokemonDetailRepository = PokemonDetailRepository(apolloClient)
    private val pokemonMovesRepository = PokemonMovesRepository(apolloClient)

    private val pokemonDetail = mutableStateOf(PokemonDetailPresentationModel.bulbasaur) // super temporary
    private val pokemonMoves = mutableStateListOf<PokemonMovePresentationData>()
    private val loading = mutableStateOf(true)
    private val error = mutableStateOf(false)

    init {
        fetchData()
    }

    fun isLoading(): State<Boolean> = loading

    fun hasError(): State<Boolean> = error

    fun getPokemonDetails() = pokemonDetail

    fun getPokemonMoves() = pokemonMoves

    private fun fetchData() {
        viewModelScope.launch {
            // i'm gonna try to load all the details and image of the pokemon once we get most of the info
            val detailsRessult = pokemonDetailRepository.fetchPokemonDetails(pokemonId)
            if (detailsRessult is PokemonDetailRepository.PokemonDetailResult.Success) {
                pokemonDetail.value = detailsRessult.data
            } else {
                error.value = true
            }
            loading.value = false

            // I'm not worrying TOO much about this failing, if it does, it just won't display the moves
            val movesResult = pokemonMovesRepository.fetchMovesForPokemon(pokemonId)
            if (movesResult is PokemonMovesRepository.PokemonMovesResult.Success) {
                pokemonMoves.addAll(movesResult.data)
            }
        }
    }

    fun retry() {
        loading.value = true
        error.value = false
        fetchData()
    }
}

class PokemonDetailViewModelFactory(
    private val apolloClient: ApolloClient,
    private val pokemonId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(apolloClient, pokemonId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}