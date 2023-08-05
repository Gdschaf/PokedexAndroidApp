package com.radhangs.pokedexapp.pokedex

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import java.lang.IllegalArgumentException
import kotlinx.coroutines.launch

class PokedexViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    private val pokedexRepository = PokedexRepository(apolloClient)

    // can this be put into a state class?
    private val pokedexList = mutableStateListOf<PokedexPresentationModel>()
    private val loading = mutableStateOf(true)
    private val error = mutableStateOf(false)

    init {
        fetchData()
    }

    fun getPokedexList() = pokedexList

    fun isLoading(): State<Boolean> = loading

    fun hasError(): State<Boolean> = error

    private fun fetchData() {
        viewModelScope.launch {

            // I had a coroutine timeout here but apollo will eventually time out itself, it takes a while
            // and I was having issues trying to cancel the on going query after a set amount of time
            // it also dawned on me that maybe not everyone has the fastest internet
            // so whatever timeout time I pick might be too short for some people
            // so i'll let apollo figure out the timeout for me

            val result = pokedexRepository.fetchPokedex()
            if(result is PokedexRepository.PokedexResult.Success) {
                pokedexList.addAll(result.data)
            } else {
                error.value = true
            }
            loading.value = false
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