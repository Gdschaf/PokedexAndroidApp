package com.radhangs.pokedexapp.pokemondetail

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel
import com.radhangs.pokedexapp.repository.PokemonDetailRepository
import com.radhangs.pokedexapp.repository.PokemonMovesRepository
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.getDominantColorFromBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class PokemonDetailViewModel(
    private val apolloClient: ApolloClient,
    private val pokemonId: Int
) : ViewModel() {
    private val pokemonDetailRepository = PokemonDetailRepository(apolloClient)
    private val pokemonMovesRepository = PokemonMovesRepository(apolloClient)

    private val pokemonDetail = mutableStateOf(PokemonDetailPresentationModel.bulbasaur) // super temporary
    private val pokemonMoves = mutableStateListOf<PokemonMovePresentationModel>()
    private val pokemonBitmap = mutableStateOf<Bitmap?>(null)
    private val pokemonDominantColor = mutableStateOf(Color.Transparent)
    private val loading = mutableStateOf(true)
    private val error = mutableStateOf(false)

    init {
        fetchData()
    }

    fun isLoading(): State<Boolean> = loading

    fun hasError(): State<Boolean> = error

    fun getPokemonDetails() = pokemonDetail

    fun getPokemonMoves() = pokemonMoves

    fun getPokemonBitmap() = pokemonBitmap

    fun getPokemonDominantColor() = pokemonDominantColor

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

    fun loadLargeBitmap(context: Context) {
        viewModelScope.launch {
            val imageUrl = Constants.LARGE_POKEMON_IMAGE_URL + getFormattedImageFilename(pokemonDetail.value.pokemonId)
            pokemonBitmap.value = getBitmapFromUrl(context, imageUrl)
            pokemonDominantColor.value = pokemonBitmap.value?.let { getDominantColorFromBitmap(it) } ?: Color.Transparent
        }
    }

    private suspend fun getBitmapFromUrl(context: Context, imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            val imageLoader = ImageLoader.Builder(context)
                .crossfade(true)
                .allowHardware(false)
                .build()

            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .build()

            when (val result = imageLoader.execute(request)) {
                is SuccessResult -> result.drawable.toBitmap()
                else -> null
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