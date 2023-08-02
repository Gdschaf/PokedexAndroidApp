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
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.IllegalArgumentException

// wouldn't it be nice if these were injectable :kekw:
class PokedexViewModel(private val apolloClient: ApolloClient) : ViewModel() {
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
                // we'll use a 10 second timeout here, no retries since there's a retry screen/button
                // I haven't fully gotten it to work, if you restore internet, it doesn't seem to work and I'm not sure why
                val result = withTimeout(10000) {
                    pokedexRepository.fetchPokedex()
                }
                if(result is PokedexRepository.PokedexResult.Success) {
                    composablePokedexList.addAll(result.data)
                } else {
                    error.value = true
                }
                loading.value = false
            } catch (e: TimeoutCancellationException) {
                loading.value = false
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