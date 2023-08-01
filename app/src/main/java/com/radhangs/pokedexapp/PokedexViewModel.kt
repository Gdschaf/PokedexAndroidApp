package com.radhangs.pokedexapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import com.radhangs.pokedexapp.repository.PokemonTypeRepository
import kotlinx.coroutines.launch

// wouldn't it be nice if these were injectable :kekw:
class PokedexViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    private val pokemonTypeRepository = PokemonTypeRepository(apolloClient)
    private val pokedexRepository = PokedexRepository(apolloClient)

    // can this be put into a state class?
    private val composablePokedexList = mutableStateListOf<PokedexPresentationModel>()
    private val loading = mutableStateOf(true)

    init {
        fetchData()
    }

    fun getLazyListData() = composablePokedexList

    fun isLoading(): State<Boolean> = loading

    private fun fetchData() {
        viewModelScope.launch {
            try {
                pokemonTypeRepository.fetchPokemonTypes()
                // not sure if i need to put a break here to wait or not... we'll find out!
                pokedexRepository.fetchPokedex(pokemonTypeRepository)
                composablePokedexList.addAll(pokedexRepository.getPokedexPokemon())
                loading.value = false
            } catch (e: ApolloException) {
                // TODO handle errors
            }
        }
    }
}