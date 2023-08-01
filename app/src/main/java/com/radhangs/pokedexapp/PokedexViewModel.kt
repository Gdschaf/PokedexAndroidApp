package com.radhangs.pokedexapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import com.radhangs.pokedexapp.repository.PokemonTypeRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// wouldn't it be nice if these were injectable :kekw:
class PokedexViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    private val pokemonTypeRepository = PokemonTypeRepository(apolloClient)
    private val pokedexRepository = PokedexRepository(apolloClient)

    // can this be put into a state class?
    private val composablePokedexList = mutableStateListOf<PokedexPresentationModel>()
    private val loading = mutableStateOf(true)
    private val error = mutableStateOf(false)

    init {
        fetchData()
    }

    fun getLazyListData() = composablePokedexList

    fun isLoading(): State<Boolean> = loading

    fun hasError(): State<Boolean> = error

    private fun fetchData() {
        viewModelScope.launch {
            try {
                pokemonTypeRepository.fetchPokemonTypes()
                pokedexRepository.fetchPokedex(pokemonTypeRepository)
                composablePokedexList.addAll(pokedexRepository.getPokedexPokemon())
                loading.value = false
            } catch (e: ApolloException) {
                error.value = true
            }
        }
    }

    fun retry() {
        loading.value = true
        error.value = false
        fetchData()
    }
}

class PokedexViewModelFactory(private val apolloClient: ApolloClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokedexViewModel::class.java)) {
            return PokedexViewModel(apolloClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}