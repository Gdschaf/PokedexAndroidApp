package com.radhangs.pokedexapp

import androidx.compose.runtime.mutableStateListOf
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

    init {
        fetchData()
    }

    fun getLazyListData() = composablePokedexList

    private fun fetchData() {
        viewModelScope.launch {
            try {
                pokemonTypeRepository.fetchPokemonTypes()
                // not sure if i need to put a break here to wait or not... we'll find out!
                pokedexRepository.fetchPokedex(pokemonTypeRepository)
                composablePokedexList.addAll(pokedexRepository.getPokedexPokemon())
            } catch (e: ApolloException) {
                // TODO handle errors
            }
        }
    }
}