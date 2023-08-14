package com.radhangs.pokedexapp.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val selectedPokemonRepository: SelectedPokemonRepository
) : ViewModel() {

    private val _state = MutableLiveData<PokedexViewState>()
    val viewState : LiveData<PokedexViewState> get() = _state

    init {
        _state.value = PokedexViewState()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {

            // I had a coroutine timeout here but apollo will eventually time out itself, it takes a while
            // and I was having issues trying to cancel the on going query after a set amount of time
            // it also dawned on me that maybe not everyone has the fastest internet
            // so whatever timeout time I pick might be too short for some people
            // so i'll let apollo figure out the timeout for me

            val result = pokedexRepository.fetchPokedex()

            withContext(Dispatchers.Main) {
                if (result is PokedexRepository.PokedexResult.Success) {
                    _state.value = _state.value!!.copy(loading = false, pokedexList = result.data)
                } else {
                    _state.value = _state.value!!.copy(loading = false, error = true)
                }
            }
        }
    }

    fun retry() {
        _state.value = _state.value!!.copy(loading = true, error = false)
        fetchData()
    }

    fun setSelectedPokemon(pokemonId: Int)
    {
        selectedPokemonRepository.setSelectedPokemon(pokemonId)
    }

    data class PokedexViewState (
        val pokedexList: List<PokedexPresentationModel> = emptyList(),
        val selectedPokemonId: Int? = null,
        val loading: Boolean = true,
        val error: Boolean = false
    )
}