package com.radhangs.pokedexapp.pokemondetail

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel
import com.radhangs.pokedexapp.repository.PokemonDetailRepository
import com.radhangs.pokedexapp.repository.PokemonMovesRepository
import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.getDominantColorFromBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonDetailRepository: PokemonDetailRepository,
    private val pokemonMovesRepository: PokemonMovesRepository,
    private val selectedPokemonRepository: SelectedPokemonRepository
) : ViewModel() {

    private val _state = MutableLiveData<PokemonDetailViewState>()
    val viewState : LiveData<PokemonDetailViewState> get() = _state

    init {
        _state.value = PokemonDetailViewState()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonId = selectedPokemonRepository.getSelectedPokemon().value ?: 1

            // I decided to load the details first, show the page, then load the image and moves
            // it's not too jarring and presents the user with something to view/read
            val detailsRessult = pokemonDetailRepository.fetchPokemonDetails(pokemonId)

            withContext(Dispatchers.Main) {
                if (detailsRessult is PokemonDetailRepository.PokemonDetailResult.Success) {
                    _state.value =
                        _state.value!!.copy(loading = false, pokemonDetail = detailsRessult.data)
                } else {
                    _state.value = _state.value!!.copy(loading = false, error = true)
                }
            }

            // I'm not worrying TOO much about this failing
            // if it does, it just won't display the moves
            val movesResult = pokemonMovesRepository.fetchMovesForPokemon(pokemonId)

            withContext(Dispatchers.Main) {
                if (movesResult is PokemonMovesRepository.PokemonMovesResult.Success) {
                    _state.value = _state.value!!.copy(pokemonMoves = movesResult.data)
                }
            }
        }
    }

    fun retry() {
        _state.value = _state.value!!.copy(loading = true, error = false)
        fetchData()
    }

    // rather then having a painter load this image, we're doing it separately so we can
    // get the image's bitmap for the dominant color background.
    fun loadLargeBitmap(context: Context) {
        viewModelScope.launch {
            val imageUrl = Constants.LARGE_POKEMON_IMAGE_URL +
                    getFormattedImageFilename(selectedPokemonRepository.getSelectedPokemon().value ?: 1)
            val bitmap = getBitmapFromUrl(context, imageUrl)
            val dominantColor = bitmap?.let {
                getDominantColorFromBitmap(it)
            } ?: Color.Transparent
            _state.value = _state.value!!.copy(largeImageBitmap = bitmap, largeImageDominantColor = dominantColor)
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

    private fun getFormattedImageFilename(
        pokemonId: Int
    ): String = String.format("%03d.png", pokemonId)

    data class PokemonDetailViewState (
        val pokemonDetail: PokemonDetailPresentationModel = PokemonDetailPresentationModel.empty,
        val pokemonMoves: List<PokemonMovePresentationModel> = emptyList(),
        val largeImageBitmap: Bitmap? = null,
        val largeImageDominantColor: Color = Color.Transparent,
        val loading: Boolean = true,
        val error: Boolean = false
    )
}