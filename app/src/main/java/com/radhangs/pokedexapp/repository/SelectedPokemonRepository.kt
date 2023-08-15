package com.radhangs.pokedexapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.ViewModelScoped

// Shared repository used to inject/pass in the selected pokemon's id from the pokedex screen to the pokemon details screen
@ViewModelScoped
class SelectedPokemonRepository {
    private val selectedPokemon = MutableLiveData<Int>()

    fun getSelectedPokemon(): LiveData<Int> = selectedPokemon

    fun setSelectedPokemon(pokemonId: Int) {
        selectedPokemon.postValue(pokemonId)
    }
}
