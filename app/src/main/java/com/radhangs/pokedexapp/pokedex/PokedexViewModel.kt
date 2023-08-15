package com.radhangs.pokedexapp.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radhangs.pokedexapp.model.PokedexPresentationModel
import com.radhangs.pokedexapp.repository.PokedexRepository
import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import com.radhangs.pokedexapp.shared.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/* I don't like the fact that this view model class is open, long story short, it's for testing
   I know this should be designed/architected better for testing to not be open, and I do prefer
   to test with how the class is used. I ran into an issue with Kotlin coroutines while testing
   where it'll complain about setting the context of a coroutine and tell me to use Dispatchers.setMain()
   which I was doing for the unconfirmed and standard test coroutine scopes. The error I was getting was super intermittent
   It'd pass all tests for a while and then start failing them randomly. Doing some googling showed that this may
   be a bug as people were saying they don't think Dispatchers.setMain() did anything. who knows.
   I'd rather have all my tests actually pass then take the gamble roll of the dice and have them not */

@HiltViewModel
open class PokedexViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val selectedPokemonRepository: SelectedPokemonRepository
) : ViewModel() {

    private val _state = MutableLiveData<PokedexViewState>()
    val viewState : LiveData<PokedexViewState> get() = _state

    init {
        _state.value = PokedexViewState()
    }

    open fun fetchData() {
        if (!shouldFetchData()) {
            return
        }
        _state.value = _state.value!!.copy(loadingState = LoadingState.LOADING)

        Log.e("GARRETT", "Pokedex fetchData() is being called")
        viewModelScope.launch(Dispatchers.IO) {

            /* I had a coroutine timeout here but apollo will eventually time out itself, it takes a while
               and I was having issues trying to cancel the on going query after a set amount of time
               it also dawned on me that maybe not everyone has the fastest internet
               so whatever timeout time I pick might be too short for some people
               so i'll let apollo figure out the timeout for me */

            val result = pokedexRepository.fetchPokedex()
            withContext(Dispatchers.Main) {
                processPokedexResult(result)
            }
        }
    }

    protected fun processPokedexResult(result: PokedexRepository.PokedexResult) {
        if (result is PokedexRepository.PokedexResult.Success) {
            _state.value = _state.value!!.copy(loadingState = LoadingState.INITIALIZED, pokedexList = result.data)
        } else {
            _state.value = _state.value!!.copy(loadingState = LoadingState.ERROR)
        }
    }

    fun retry() {
        _state.value = _state.value!!.copy(loadingState = LoadingState.UNINITIALIZED)
        fetchData()
    }

    fun setSelectedPokemon(pokemonId: Int)
    {
        selectedPokemonRepository.setSelectedPokemon(pokemonId)
    }

    private fun shouldFetchData(): Boolean = viewState.value?.let {
        it.loadingState == LoadingState.UNINITIALIZED
    } ?: true

    data class PokedexViewState (
        val pokedexList: List<PokedexPresentationModel> = emptyList(),
        val loadingState: LoadingState = LoadingState.UNINITIALIZED
    )
}